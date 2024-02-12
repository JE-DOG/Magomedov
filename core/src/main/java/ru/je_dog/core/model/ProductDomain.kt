package ru.je_dog.core.model

interface ProductDomain{
    val id: Int
    val title: String
    val price: Double
    val description: String
    val category: String
    val image: String
    val rating: RatingDomain
}