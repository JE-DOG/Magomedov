package ru.je_dog.films.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.model.FilmPresentation
import ru.je_dog.core.feature.model.toPresentation
import ru.je_dog.tinkoff_school.data.films.FilmsRepository
import javax.inject.Inject

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
): ViewModel() {

    private val listFilter = FilmsFilter()

    private val _films: MutableStateFlow<List<FilmPresentation>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<FilmPresentation>> = _films

    private val _filteredFilms: MutableStateFlow<List<FilmPresentation>> = MutableStateFlow(emptyList())
    val filteredFilms: StateFlow<List<FilmPresentation>> = _filteredFilms

    private val _showError: MutableStateFlow<String?> = MutableStateFlow(null)
    val showError: StateFlow<String?> = _showError

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _screen: MutableStateFlow<ScreenType> = MutableStateFlow(ScreenType.Popular)
    val screen: StateFlow<ScreenType?> = _screen

    private val scope = CoroutineScope(Dispatchers.IO + CoroutineName("ProductsViewModel"))

    fun updateSearchInput(input: String){
        listFilter.updateSubstringFilterItem(input)
        _filteredFilms.update {
            listFilter.getFilteredList(films.value)
        }
    }

    fun getTop100Films() = scope.launch {
        val favorites = filmsRepository.getFavorites().first()
            .map { filmDomain ->
                filmDomain.toPresentation()
                    .copy(isFavorite = true)
            }

        filmsRepository.getTop100Films()
            .onStart {
                _isLoading.update { true }
            }
            .catch {
                _showError.update {
                    "Что-то пошло не так"
                }
                _showError.update { null }
                _films.update { favorites }
                _filteredFilms.update {
                    listFilter.getFilteredList(favorites)
                }
            }
            .map { filmsDomain ->
                filmsDomain.map { filmDomain ->
                    val favoriteFilm = favorites.firstOrNull { it.filmId == filmDomain.filmId }
                    if (favoriteFilm != null){
                        favoriteFilm
                    }else {
                        filmDomain.toPresentation()
                    }
                }
            }
            .onCompletion {
                _isLoading.update { false }
            }
            .collect { newFilms ->
                _films.update {
                    newFilms
                }
                _filteredFilms.update {
                    listFilter.getFilteredList(newFilms)
                }
            }
    }

    fun updateScreen(screenType: ScreenType) {
        when(screenType){

            ScreenType.Favorites -> {
                listFilter.filterByFavorite(true)
                _filteredFilms.update {
                    listFilter.getFilteredList(films.value)
                }
            }

            ScreenType.Popular -> {
                listFilter.filterByFavorite(false)
                _filteredFilms.update {
                    listFilter.getFilteredList(films.value)
                }
            }

        }
        _screen.update { screenType }
    }

    fun saveToFavorites(film: FilmPresentation) = scope.launch {
        filmsRepository.saveToFavorites(film)
        val newList = films.value.toMutableList()
        val index = newList.lastIndexOf(film)
        if (index != -1){
            newList[index] = film.copy(isFavorite = true)
            _films.update {
                newList
            }
            _filteredFilms.update {
                listFilter.getFilteredList(newList)
            }
        }
    }

    fun deleteFromFavorites(film: FilmPresentation) = scope.launch {
        filmsRepository.saveToFavorites(film)
        val newList = films.value.toMutableList()
        val index = newList.lastIndexOf(film)
        if (index != -1){
            newList[index] = film.copy(isFavorite = false)
            _films.update {
                newList
            }
            _filteredFilms.update {
                listFilter.getFilteredList(newList)
            }
        }
    }

    class Factory @Inject constructor(
        private val productRepository: FilmsRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilmsViewModel(productRepository) as T
        }
    }
}