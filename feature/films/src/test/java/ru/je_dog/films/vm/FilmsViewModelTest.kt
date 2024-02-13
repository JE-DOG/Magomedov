package ru.je_dog.films.vm

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ru.je_dog.core.feature.model.FilmPresentation
import ru.je_dog.tinkoff_school.data.films.FilmsRepository

class FilmsViewModelTest {

    private lateinit var repository: FilmsRepository
    private lateinit var viewModel: FilmsViewModel

    @Before
    fun beforeEach(){
        repository = mock()
        viewModel = FilmsViewModel(repository)
    }

    @Test
    fun getTop100Films(): Unit = runBlocking {
        //Ready
        val expect = List(10){ FilmPresentation() }
        val filmsFlow: Flow<List<FilmPresentation>> = flow { emit(expect) }
        `when`(repository.getTop100Films()).thenReturn(filmsFlow)
        `when`(repository.getFavorites()).thenReturn(flow { emit(emptyList()) })
        //Call
        viewModel.getTop100Films()
        //assert
        delay(500)
        val actualFilms = viewModel.films.value
        val actualFiltered = viewModel.filteredFilms.value
        Assert.assertEquals(expect,actualFilms)
        Assert.assertEquals(expect,actualFiltered)
    }

    @Test
    fun getFavoritesWhenThrow(): Unit = runBlocking {
        //Ready
        val expect = List(10){ FilmPresentation(isFavorite = true) }
        val filmsFlow: Flow<List<FilmPresentation>> = flow { emit(expect) }
        `when`(repository.getTop100Films()).thenReturn(flow { throw Exception() })
        `when`(repository.getFavorites()).thenReturn(filmsFlow)
        //Call
        viewModel.getTop100Films()
        //assert
        delay(500)
        val actualFilms = viewModel.films.value
        val actualFiltered = viewModel.filteredFilms.value
        Assert.assertEquals(expect,actualFilms)
        Assert.assertEquals(expect,actualFiltered)
    }

    @Test
    fun updateScreen() = runBlocking {
        //Ready
        val expect = ScreenType.Popular
        //Call
        viewModel.updateScreen(expect)
        //Assert
        delay(500)
        val actual = viewModel.screen.value
        Assert.assertEquals(expect,actual)
    }

    @Test
    fun saveToFavorite() = runBlocking {
        //Ready
        val mockList = List(10){ FilmPresentation(filmId = it) }
        val filmsFlow: Flow<List<FilmPresentation>> = flow { emit(mockList) }
        `when`(repository.getTop100Films()).thenReturn(filmsFlow)
        `when`(repository.getFavorites()).thenReturn(flow { emit(emptyList()) })
        //Call
        viewModel.getTop100Films()
        delay(500)
        viewModel.saveToFavorites(viewModel.films.value[0])
        //assert
        delay(500)
        val expect = FilmPresentation(isFavorite = true)
        val actual = viewModel.films.value[0]
        Assert.assertEquals(expect,actual)
    }

    @Test
    fun deleteFromFavorite() = runBlocking {
        //Ready
        val mockList = List(10){ FilmPresentation(filmId = it) }
        val filmsFlow: Flow<List<FilmPresentation>> = flow { emit(mockList) }
        `when`(repository.getTop100Films()).thenReturn(filmsFlow)
        `when`(repository.getFavorites()).thenReturn(flow { emit(emptyList()) })
        //Call
        viewModel.getTop100Films()
        delay(500)
        viewModel.deleteFromFavorites(viewModel.films.value[0])
        //assert
        delay(500)
        val expect = FilmPresentation(isFavorite = false)
        val actual = viewModel.films.value[0]
        Assert.assertEquals(expect,actual)
    }

}