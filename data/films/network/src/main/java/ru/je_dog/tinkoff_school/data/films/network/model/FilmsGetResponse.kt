package ru.je_dog.tinkoff_school.data.films.network.model

import ru.je_dog.core.network.model.product.FilmJson

data class FilmsGetResponse(
    val films: List<FilmJson>
)
