package ru.je_dog.core.model

data class ProductDomain(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDomain
)

class RatingDomain(
    val rate: Float,
    val count: Int
)