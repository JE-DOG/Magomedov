package ru.je_dog.tinkoff_school.data.films.storage

import ru.je_dog.core.model.FilmDomain
import ru.je_dog.core.source.StorageSource
import ru.je_dog.tinkoff_school.core.data.storage.model.FilmEntity

interface FavoritesFilmsStorageDataSource: StorageSource<FilmDomain> {

    suspend fun getAll(): List<FilmDomain>

    suspend fun delete(film: FilmEntity)

    suspend fun save(film: FilmEntity)

}