package ru.je_dog.products.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.model.DetailFilmPresentation
import ru.je_dog.core.feature.model.FilmPresentation
import ru.je_dog.core.feature.model.toPresentation
import ru.je_dog.tinkoff_school.data.films.FilmsRepository
import javax.inject.Inject

class DetailFilmViewModel(
    private val filmsRepository: FilmsRepository
): ViewModel() {

    private val _film: MutableStateFlow<DetailFilmPresentation?> = MutableStateFlow(null)
    val film: StateFlow<DetailFilmPresentation?> = _film
    private val _showError: MutableStateFlow<String?> = MutableStateFlow(null)
    val showError: StateFlow<String?> = _showError
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val scope = CoroutineScope(Dispatchers.IO + CoroutineName("DetailFilmViewModel"))

    fun getFilm(id: Int) = scope.launch {
        filmsRepository.getById(id)
            .onStart {
                _isLoading.update { true }
            }
            .catch {
                _showError.update { "Что-то пошло не так" }
            }
            .onCompletion {
                _isLoading.update { false }
            }
            .collect { film ->
                _film.update { film.toPresentation() }
            }
    }

    class Factory @Inject constructor(
        private val productRepository: FilmsRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailFilmViewModel(productRepository) as T
        }
    }
}