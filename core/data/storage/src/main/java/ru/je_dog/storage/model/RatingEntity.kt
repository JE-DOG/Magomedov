package ru.je_dog.storage.model

import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.model.RatingDomain

data class RatingEntity(
    override val rate: Float,
    override val count: Int
): RatingDomain
