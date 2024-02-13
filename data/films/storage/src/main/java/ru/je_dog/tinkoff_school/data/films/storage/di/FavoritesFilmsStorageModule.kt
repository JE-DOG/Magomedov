package ru.je_dog.tinkoff_school.data.films.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.je_dog.core.app.AppBuildConfig
import ru.je_dog.tinkoff_school.data.films.storage.FavoritesFilmsStorageDataSource
import ru.je_dog.tinkoff_school.data.films.storage.FavoritesFilmsStorageDataSourceImpl
import ru.je_dog.tinkoff_school.data.films.storage.room.FavoritesFilmsDao
import ru.je_dog.tinkoff_school.data.films.storage.room.ProductsRoomDataBaseFactory
import ru.je_dog.tinkoff_school.data.films.storage.room.FavoritesFilmsRoomDataBase

/**
 * @param Context
 * @param AppBuildConfig
* */
@Module
class FavoritesFilmsStorageModule {

    @Provides
    fun provideProductsRoomDataBaseFactory(
        context: Context,
        appBuildConfig: AppBuildConfig
    ): ProductsRoomDataBaseFactory {
        return if (appBuildConfig.isDebug){
            ProductsRoomDataBaseFactory.Mock(context)
        }else {
            ProductsRoomDataBaseFactory.Base(context)
        }
    }

    @Provides
    fun providerProductsRoomDataBase(
        dataBaseFactory: ProductsRoomDataBaseFactory
    ): FavoritesFilmsRoomDataBase {
        return dataBaseFactory.roomDatabase
    }

    @Provides
    fun provideProductsDao(
        productsRoomDataBase: FavoritesFilmsRoomDataBase
    ): FavoritesFilmsDao {
        return productsRoomDataBase.favoritesFilmsDao()
    }

    @Provides
    fun provideProductsStorageDataSource(
        productsDao: FavoritesFilmsDao
    ): FavoritesFilmsStorageDataSource {
        return FavoritesFilmsStorageDataSourceImpl(productsDao)
    }

}