package ru.je_dog.tinkoff_school.data.products.network

import ru.je_dog.core.model.ProductDomain

class ProductsNetworkDataSourceImpl(
    private val api: ProductsApi
): ProductsNetworkDataSource {

    override suspend fun getAll(): List<ProductDomain> = api.getProducts()

}