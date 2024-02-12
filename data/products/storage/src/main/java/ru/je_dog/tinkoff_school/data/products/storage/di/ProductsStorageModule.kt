package ru.je_dog.tinkoff_school.data.products.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.je_dog.core.app.AppBuildConfig
import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.source.StorageSource
import ru.je_dog.tinkoff_school.data.products.storage.ProductsStorageDataSourceImpl
import ru.je_dog.tinkoff_school.data.products.storage.room.ProductsDao
import ru.je_dog.tinkoff_school.data.products.storage.room.ProductsRoomDataBaseFactory
import ru.je_dog.tinkoff_school.data.products.storage.room.ProductsRoomDataBase

/**
 * @param Context
 * @param AppBuildConfig
* */
@Module
class ProductsStorageModule {

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
    ): ProductsRoomDataBase {
        return dataBaseFactory.roomDatabase
    }

    @Provides
    fun provideProductsDao(
        productsRoomDataBase: ProductsRoomDataBase
    ): ProductsDao {
        return productsRoomDataBase.productsDao()
    }

    @Provides
    fun provideProductsStorageDataSource(
        productsDao: ProductsDao
    ): StorageSource<ProductDomain> {
        return ProductsStorageDataSourceImpl(productsDao)
    }

}