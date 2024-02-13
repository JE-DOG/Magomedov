package ru.je_dog.tinkoff_school.data.films.network

import ru.je_dog.core.model.DetailFilmDomain
import ru.je_dog.core.model.FilmDomain
import ru.je_dog.core.source.NetworkSource

interface FilmsNetworkDataSource: NetworkSource<FilmDomain> {

    suspend fun getTop100(): List<FilmDomain>

    suspend fun getById(id: Int): DetailFilmDomain

}