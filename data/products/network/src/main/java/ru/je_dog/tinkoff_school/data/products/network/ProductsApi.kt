package ru.je_dog.tinkoff_school.data.products.network

import retrofit2.http.GET
import ru.je_dog.core.network.model.product.ProductJson

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): List<ProductJson>

}