package ru.je_dog.tinkoff_school.data.films

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.je_dog.core.model.FilmDomain
import ru.je_dog.tinkoff_school.core.data.storage.model.FilmEntity
import ru.je_dog.tinkoff_school.data.films.network.FilmsNetworkDataSource
import ru.je_dog.tinkoff_school.data.films.storage.FavoritesFilmsStorageDataSource

class ProductsRepositoryImpl(
    private val networkSource: FilmsNetworkDataSource,
    private val storageSource: FavoritesFilmsStorageDataSource
): FilmsRepository {

    override suspend fun saveToFavorites(film: FilmDomain) = storageSource.save(FilmEntity.fromDomain(film))

    override suspend fun deleteFromFavorites(film: FilmDomain) = storageSource.delete(FilmEntity.fromDomain(film))

    override fun getTop100Films(): Flow<List<FilmDomain>> = flow {
        emit(networkSource.getTop100())
    }

    override fun getFavorites(): Flow<List<FilmDomain>> = flow{
        emit(storageSource.getAll())
    }
}