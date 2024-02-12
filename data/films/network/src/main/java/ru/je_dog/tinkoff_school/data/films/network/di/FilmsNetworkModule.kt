package ru.je_dog.tinkoff_school.data.films.network.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.je_dog.core.model.FilmDomain
import ru.je_dog.core.source.NetworkSource
import ru.je_dog.tinkoff_school.data.films.network.FilmsApi
import ru.je_dog.tinkoff_school.data.films.network.FilmsNetworkDataSource
import ru.je_dog.tinkoff_school.data.films.network.ProductsNetworkDataSourceImpl

/**
 * @param Retrofit
 * */
@Module
class FilmsNetworkModule {

    @Provides
    fun provideProductsApi(
        retrofit: Retrofit
    ): FilmsApi {
        return retrofit.create()
    }

    @Provides
    fun provideProductsNetworkDataSource(
        productsApi: FilmsApi
    ): FilmsNetworkDataSource {
        return ProductsNetworkDataSourceImpl(productsApi)
    }

}