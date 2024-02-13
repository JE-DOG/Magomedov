package ru.je_dog.core.feature.model

import ru.je_dog.core.model.GenreDomain

data class GenrePresentation(
    override val genre: String
): GenreDomain()

fun GenreDomain.toPresentation() = GenrePresentation(genre)