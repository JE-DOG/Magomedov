package ru.je_dog.core.network.factory

import retrofit2.Retrofit

interface RetrofitFactory {

    fun create(): Retrofit

}