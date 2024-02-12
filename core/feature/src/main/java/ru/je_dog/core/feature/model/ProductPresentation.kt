package ru.je_dog.core.feature.model

import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.model.RatingDomain

data class ProductPresentation(
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingPresentation
)

class RatingPresentation(
    val rate: Float,
    val count: Int
)

fun ProductDomain.toPresentation() = ProductPresentation(
    id,title,"$price Руб",description,category,image,rating.toPresentation()
)

fun RatingDomain.toPresentation() = RatingPresentation(
    rate,
    count
)