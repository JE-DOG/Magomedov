package ru.je_dog.core.network.model.product

import ru.je_dog.core.model.FilmDomain

data class FilmJson(
    override val countries: List<CountryJson>,
    override val filmId: Int,
    override val filmLength: String,
    override val genres: List<GenreJson>,
    override val isAfisha: Int,
    override val nameEn: String?,
    override val nameRu: String,
    override val posterUrl: String,
    override val posterUrlPreview: String,
    override val rating: String,
    override val ratingVoteCount: Int,
    override val year: String
): FilmDomain()