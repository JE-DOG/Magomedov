package ru.je_dog.tinkoff_school.data.products.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.je_dog.core.model.ProductDomain
import ru.je_dog.storage.model.ProductEntity
import ru.je_dog.storage.model.toEntity
import ru.je_dog.tinkoff_school.data.products.storage.room.ProductsDao

class ProductsStorageDataSourceImpl(
    private val productsDao: ProductsDao
): ProductsStorageDataSource {

    override suspend fun getAll(): List<ProductDomain> = productsDao.getProducts()

    override suspend fun saveAll(products: List<ProductDomain>) {
        productsDao.addProducts(products.map { it.toEntity() })
    }

}