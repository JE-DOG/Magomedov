package ru.je_dog.products.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.model.FilmPresentation
import ru.je_dog.core.feature.model.toPresentation
import ru.je_dog.tinkoff_school.data.films.FilmsRepository
import javax.inject.Inject

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
): ViewModel() {

    private val _films: MutableStateFlow<List<FilmPresentation>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<FilmPresentation>> = _films

    private val _favoriteFilms: MutableStateFlow<List<FilmPresentation>> = MutableStateFlow(emptyList())
    val favoriteFilms: StateFlow<List<FilmPresentation>> = _favoriteFilms

    private val _showError: MutableStateFlow<String?> = MutableStateFlow(null)
    val showError: StateFlow<String?> = _showError

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _screen: MutableStateFlow<ScreenType> = MutableStateFlow(ScreenType.Popular)
    val screen: StateFlow<ScreenType?> = _screen

    private val scope = CoroutineScope(Dispatchers.IO + CoroutineName("ProductsViewModel"))

    fun getTop100Films() = scope.launch {
        filmsRepository.getTop100Films()
            .catch {
                Log.d("SomeTag",it.message.toString())
                Log.d("SomeTag",it.localizedMessage.toString())
                _showError.update {
                    "Что-то пошло не так"
                }
            }
            .collect { newProducts ->
                _films.update {
                    newProducts.map { productDomain ->
                        productDomain.toPresentation()
                    }
                }
            }
    }

    fun updateScreen(screenType: ScreenType) = _screen.update { screenType }

    fun saveToFavorites(film: FilmPresentation) = scope.launch {
        filmsRepository.saveToFavorites(film)
        _films.update {  currentState ->
            currentState.map { mapFilm ->
                if (mapFilm == film){
                    mapFilm.copy(isFavorite = true)
                }else {
                    mapFilm
                }
            }
        }
    }

    fun deleteFromFavorites(film: FilmPresentation) = scope.launch {
        filmsRepository.deleteFromFavorites(film)
        _films.update {  currentState ->
            val newList = currentState.toMutableList()
            newList.remove(film)
            newList
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