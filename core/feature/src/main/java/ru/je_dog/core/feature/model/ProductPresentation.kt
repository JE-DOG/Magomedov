package ru.je_dog.core.feature.model

data class ProductPresentation(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingPresentation
)

class RatingPresentation(
    val rate: Float,
    val count: Int
)
