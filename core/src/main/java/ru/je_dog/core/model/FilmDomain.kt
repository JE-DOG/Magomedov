package ru.je_dog.core.model

abstract class FilmDomain {
    abstract val filmId: Int
    abstract val countries: List<CountryDomain>
    abstract val filmLength: String
    abstract val genres: List<GenreDomain>
    abstract val isAfisha: Int
    abstract val nameEn: String?
    abstract val nameRu: String
    abstract val posterUrl: String
    abstract val posterUrlPreview: String
    abstract val rating: String
    abstract val ratingVoteCount: Int
    abstract val year: String
}