package ru.je_dog.tinkoff_school.data.films.storage

import ru.je_dog.core.model.FilmDomain
import ru.je_dog.tinkoff_school.core.data.storage.model.FilmEntity
import ru.je_dog.tinkoff_school.data.films.storage.room.FavoritesFilmsDao

class FavoritesFilmsStorageDataSourceImpl(
    private val favoritesFilmsDao: FavoritesFilmsDao
): FavoritesFilmsStorageDataSource {

    override suspend fun getAll(): List<FilmDomain> {
        return favoritesFilmsDao.getAll().map { it.toDomain() }
    }

    override suspend fun delete(film: FilmEntity) {
        favoritesFilmsDao.delete(film)
    }

    override suspend fun save(film: FilmEntity) {
        favoritesFilmsDao.save(film)
    }

}