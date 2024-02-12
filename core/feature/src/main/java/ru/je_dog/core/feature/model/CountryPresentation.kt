package ru.je_dog.core.feature.model

import ru.je_dog.core.model.CountryDomain

data class CountryPresentation(
    override val country: String
): CountryDomain()

fun CountryDomain.toPresentation() = CountryPresentation(
    country
)