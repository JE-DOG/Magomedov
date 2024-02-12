package ru.je_dog.tinkoff_school.data.di

import dagger.Module
import dagger.Provides
import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.repository.Repository
import ru.je_dog.core.source.NetworkSource
import ru.je_dog.core.source.StorageSource
import ru.je_dog.tinkoff_school.data.products.ProductsRepositoryImpl
import ru.je_dog.tinkoff_school.data.products.network.di.ProductsNetworkModule
import ru.je_dog.tinkoff_school.data.products.storage.di.ProductsStorageModule

/**
 * @param NetworkSource  with ProductDomain
 * @param StorageSource  with ProductDomain
 * */
@Module(
    includes = [
        ProductsStorageModule::class,
        ProductsNetworkModule::class
    ]
)
class ProductsDataModule {

    @Provides
    fun provideProductsRepository(
        networkSource: NetworkSource<ProductDomain>,
        storageSource: StorageSource<ProductDomain>
    ): Repository<ProductDomain>{
        return ProductsRepositoryImpl(
            networkDataSource = networkSource,
            storageDataSource = storageSource
        )
    }

}