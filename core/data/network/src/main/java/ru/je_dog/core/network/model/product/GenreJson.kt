package ru.je_dog.core.network.model.product

import ru.je_dog.core.model.GenreDomain

data class GenreJson(
    override val genre: String
): GenreDomain()