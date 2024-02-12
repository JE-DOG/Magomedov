package ru.je_dog.tinkoff_school.data.films.network

import android.util.Log
import ru.je_dog.core.model.DetailFilmDomain
import ru.je_dog.core.model.FilmDomain

class FilmsNetworkDataSourceImpl(
    private val api: FilmsApi
): FilmsNetworkDataSource {

    override suspend fun getTop100(): List<FilmDomain> = api.getTop100().films

    override suspend fun getById(id: Int): DetailFilmDomain {
        Log.d("idTag",id.toString())
        return api.getById(id)
    }

}