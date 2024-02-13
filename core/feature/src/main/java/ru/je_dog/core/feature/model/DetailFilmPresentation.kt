package ru.je_dog.core.feature.model

import ru.je_dog.core.model.DetailFilmDomain

data class DetailFilmPresentation(
    override val nameRu: String,
    override val coverUrl: String,
    override val shortDescription: String,
    override val genres: List<GenrePresentation>,
    override val countries: List<CountryPresentation>
): DetailFilmDomain()

fun DetailFilmDomain.toPresentation() = DetailFilmPresentation(
    nameRu, coverUrl, shortDescription, genres.map { GenrePresentation(it.genre) }, countries.map { CountryPresentation(it.country) }
)
