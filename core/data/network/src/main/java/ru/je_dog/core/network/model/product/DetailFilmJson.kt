package ru.je_dog.core.network.model.product

import com.google.gson.annotations.SerializedName
import ru.je_dog.core.model.DetailFilmDomain

data class DetailFilmJson(
    override val nameRu: String,
    @SerializedName("posterUrl")
    override val coverUrl: String,
    override val shortDescription: String,
    override val genres: List<GenreJson>,
    override val countries: List<CountryJson>
): DetailFilmDomain()
