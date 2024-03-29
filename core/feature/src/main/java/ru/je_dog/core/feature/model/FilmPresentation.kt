package ru.je_dog.core.feature.model

import ru.je_dog.core.model.CountryDomain
import ru.je_dog.core.model.FilmDomain
import ru.je_dog.core.model.GenreDomain
import java.lang.StringBuilder

data class FilmPresentation(
    override val filmId: Int = 0,
    override val countries: List<CountryDomain> = emptyList(),
    override val filmLength: String = "",
    override val genres: List<GenreDomain> = emptyList(),
    override val isAfisha: Int = 0,
    override val nameEn: String? = null,
    override val nameRu: String = "",
    override val posterUrl: String = "",
    override val posterUrlPreview: String = "",
    override val rating: String = "",
    override val ratingVoteCount: Int = 0,
    override val year: String = "",
    val isFavorite: Boolean = false,
    val genreAndDate: String = " ()"
): FilmDomain()

fun FilmDomain.toPresentation() = FilmPresentation(
    filmId,
    countries,
    filmLength,
    genres,
    isAfisha,
    nameEn,
    nameRu,
    posterUrl,
    posterUrlPreview,
    rating,
    ratingVoteCount,
    year,
    genreAndDate = getGenreAndDate(year,genres)
)

fun getGenreAndDate(
    year: String,
    genres: List<GenreDomain>
) = StringBuilder().apply {
    genres.forEachIndexed { index, genre ->
        append(genre.genre)
        if (index != genres.size - 1){
            append(",")
        }
    }
    append(" ($year)")
}.toString()