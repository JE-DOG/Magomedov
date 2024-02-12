package ru.je_dog.tinkoff_school.data.films

import kotlinx.coroutines.flow.Flow
import ru.je_dog.core.model.DetailFilmDomain
import ru.je_dog.core.model.FilmDomain
import ru.je_dog.tinkoff_school.core.data.Repository

interface FilmsRepository: Repository<FilmDomain> {

    suspend fun saveToFavorites(film: FilmDomain)

    suspend fun deleteFromFavorites(film: FilmDomain)

    fun getTop100Films(): Flow<List<FilmDomain>>

    fun getFavorites(): Flow<List<FilmDomain>>

    fun getById(id: Int): Flow<DetailFilmDomain>

}