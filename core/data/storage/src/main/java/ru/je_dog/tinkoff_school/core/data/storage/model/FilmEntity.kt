package ru.je_dog.tinkoff_school.core.data.storage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.je_dog.core.model.CountryDomain
import ru.je_dog.core.model.FilmDomain
import ru.je_dog.core.model.GenreDomain
import java.lang.StringBuilder

@Entity(
    tableName = FilmEntity.TABLE_NAME
)
class FilmEntity(
    @PrimaryKey(autoGenerate = false)
    val filmId: Int,
    val countries: String,
    val filmLength: String,
    val genres: String,
    val isAfisha: Int,
    val nameEn: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val year: String,
    val nameRu: String
) {

    fun toDomain(): FilmDomain{
        return object: FilmDomain() {
            override val filmId: Int = this@FilmEntity.filmId
            override val countries: List<CountryDomain> = deconvert(this@FilmEntity.countries){ CountryEntity(it) }
            override val filmLength: String = this@FilmEntity.filmLength
            override val genres: List<GenreDomain> = deconvert(this@FilmEntity.genres){ GenreEntity(it) }
            override val isAfisha: Int = this@FilmEntity.isAfisha
            override val nameEn: String? = this@FilmEntity.nameEn
            override val nameRu: String = this@FilmEntity.nameRu
            override val posterUrl: String = this@FilmEntity.posterUrl
            override val posterUrlPreview: String = this@FilmEntity.posterUrlPreview
            override val rating: String = this@FilmEntity.rating
            override val ratingVoteCount: Int = this@FilmEntity.ratingVoteCount
            override val year: String = this@FilmEntity.year
        }
    }

    private fun<T> deconvert(string: String,putter: (String) -> T): List<T>{
        val values = string.split(',')
        return values.map(putter)
    }

    companion object {
        const val TABLE_NAME = "favorites_film_table"

        fun fromDomain(film: FilmDomain) = film.run {
            val countries = convert(countries.map { it.country })
            val genres = convert(genres.map { it.genre })
            FilmEntity(
                filmId, countries, filmLength, genres, isAfisha, nameEn, posterUrl, posterUrlPreview, rating, ratingVoteCount, year, nameRu
            )
        }

        private fun convert(list: List<String>): String{
            return StringBuilder().apply {
                list.forEachIndexed { index,string ->
                    append(string)
                    if (index != list.size - 1){
                        append(",")
                    }
                }
            }.toString()
        }

    }
}