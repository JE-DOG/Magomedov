package ru.je_dog.tinkoff_school.data.products

import kotlinx.coroutines.flow.Flow
import ru.je_dog.core.model.ProductDomain


interface ProductsRepository {

    fun getProducts(): Flow<ProductDomain>

}