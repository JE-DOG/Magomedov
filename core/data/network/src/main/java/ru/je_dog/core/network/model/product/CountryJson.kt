package ru.je_dog.core.network.model.product

import ru.je_dog.core.model.CountryDomain

data class CountryJson(
    override val country: String
): CountryDomain()