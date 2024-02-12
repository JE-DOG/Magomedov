package ru.je_dog.tinkoff_school.data.products.network.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.source.NetworkSource
import ru.je_dog.tinkoff_school.data.products.network.ProductsApi
import ru.je_dog.tinkoff_school.data.products.network.ProductsNetworkDataSource
import ru.je_dog.tinkoff_school.data.products.network.ProductsNetworkDataSourceImpl

/**
 * @param Retrofit
 * */
@Module
class ProductsNetworkModule {

    @Provides
    fun provideProductsApi(
        retrofit: Retrofit
    ): ProductsApi {
        return retrofit.create()
    }

    @Provides
    fun provideProductsNetworkDataSource(
        productsApi: ProductsApi
    ): NetworkSource<ProductDomain> {
        return ProductsNetworkDataSourceImpl(productsApi)
    }

}