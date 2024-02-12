package ru.je_dog.products.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
        filmsRepository.getTop100Films()
            .catch {
                Log.d("SomeTag",it.message.toString())
                Log.d("SomeTag",it.localizedMessage.toString())
                _showError.update {
                    "Что-то пошло не так"
                }
                _showError.update { null }
            }
            .collect { newFilmsDomain ->
                _films.update {
                    newFilmsDomain.map { productDomain ->
                        productDomain.toPresentation()
                    }
                }
                _filteredFilms.update {
                    val newFilms = newFilmsDomain.map { productDomain ->
                        productDomain.toPresentation()
                    }
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
        _films.update {  currentState ->
            val newList = currentState
            newList.map { mapFilm ->
                if (newList.find { it.filmId == film.filmId } != null){
                    film.copy(isFavorite = true)
                }else {
                    mapFilm
                }
            }
            newList
        }
        _filteredFilms.update {
            listFilter.getFilteredList(_films.value)
        }
    }

    fun deleteFromFavorites(film: FilmPresentation) = scope.launch {
        filmsRepository.deleteFromFavorites(film)
        _films.update {  currentState ->
            val newList = currentState.toMutableList()
            newList.map { mapFilm ->
                if (newList.find { it.filmId == film.filmId } != null){
                    film.copy(isFavorite = false)
                }else {
                    mapFilm
                }
            }
            newList
        }
        _filteredFilms.update {
            listFilter.getFilteredList(_films.value)
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