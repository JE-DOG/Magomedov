package ru.je_dog.core.model

abstract class DetailFilmDomain {
    abstract val nameRu: String
    abstract val coverUrl: String
    abstract val shortDescription: String
    abstract val genres: List<GenreDomain>
    abstract val countries: List<CountryDomain>
}