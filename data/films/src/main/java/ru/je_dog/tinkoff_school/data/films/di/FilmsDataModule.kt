package ru.je_dog.tinkoff_school.data.films.di

import dagger.Module
import dagger.Provides
import ru.je_dog.core.source.NetworkSource
import ru.je_dog.core.source.StorageSource
import ru.je_dog.tinkoff_school.data.films.FilmsRepository
import ru.je_dog.tinkoff_school.data.films.ProductsRepositoryImpl
import ru.je_dog.tinkoff_school.data.films.network.FilmsNetworkDataSource
import ru.je_dog.tinkoff_school.data.films.network.di.FilmsNetworkModule
import ru.je_dog.tinkoff_school.data.films.storage.FavoritesFilmsStorageDataSource
import ru.je_dog.tinkoff_school.data.films.storage.di.FavoritesFilmsStorageModule

/**
 * @param NetworkSource  with ProductDomain
 * @param StorageSource  with ProductDomain
 * */
@Module(
    includes = [
        FavoritesFilmsStorageModule::class,
        FilmsNetworkModule::class
    ]
)
class FilmsDataModule {

    @Provides
    fun provideProductsRepository(
        networkSource: FilmsNetworkDataSource,
        storageSource: FavoritesFilmsStorageDataSource
    ): FilmsRepository{
        return ProductsRepositoryImpl(
            networkSource = networkSource,
            storageSource = storageSource
        )
    }

}