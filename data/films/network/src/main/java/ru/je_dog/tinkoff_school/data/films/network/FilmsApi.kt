package ru.je_dog.tinkoff_school.data.films.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.je_dog.core.network.model.product.DetailFilmJson
import ru.je_dog.core.network.model.product.FilmJson
import ru.je_dog.tinkoff_school.data.films.network.model.FilmsGetResponse

interface FilmsApi {

    @GET("api/v2.2/films/top")
    @Headers(
        "type: TOP_100_POPULAR_FILMS",
    )
    suspend fun getTop100(): FilmsGetResponse

    @GET("/api/v2.2/films/{id}")
    suspend fun getById(@Path("id") id: Int): DetailFilmJson

}