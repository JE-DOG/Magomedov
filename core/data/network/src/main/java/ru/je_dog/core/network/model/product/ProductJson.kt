package ru.je_dog.core.network.model.product

import ru.je_dog.core.model.ProductDomain

data class ProductJson(
    override val id: Int,
    override val title: String,
    override val price: Double,
    override val description: String,
    override val category: String,
    override val image: String,
    override val rating: RatingJson
): ProductDomain
