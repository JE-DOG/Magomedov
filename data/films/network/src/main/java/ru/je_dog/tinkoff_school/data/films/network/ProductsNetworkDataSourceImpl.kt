package ru.je_dog.tinkoff_school.data.films.network

import ru.je_dog.core.model.FilmDomain

class ProductsNetworkDataSourceImpl(
    private val api: FilmsApi
): FilmsNetworkDataSource {

    override suspend fun getTop100(): List<FilmDomain> = api.getTop100().films

}