package ru.je_dog.tinkoff_school.data.products

import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.repository.Repository
import ru.je_dog.core.source.NetworkSource
import ru.je_dog.core.source.StorageSource
import ru.je_dog.tinkoff_school.data.products.network.ProductsNetworkDataSource
import ru.je_dog.tinkoff_school.data.products.storage.ProductsStorageDataSource

class ProductsRepositoryImpl(
    networkDataSource: NetworkSource<ProductDomain>,
    storageDataSource: StorageSource<ProductDomain>
): Repository<ProductDomain>(
    networkSource = networkDataSource,
    storageSource = storageDataSource
)