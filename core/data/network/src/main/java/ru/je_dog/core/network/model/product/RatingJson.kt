package ru.je_dog.core.network.model.product

import ru.je_dog.core.model.RatingDomain

class RatingJson(
    override val rate: Float,
    override val count: Int
): RatingDomain