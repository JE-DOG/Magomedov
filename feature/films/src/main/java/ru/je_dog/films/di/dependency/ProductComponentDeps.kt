package ru.je_dog.films.di.dependency

import android.content.Context
import retrofit2.Retrofit
import ru.je_dog.core.app.AppBuildConfig

interface ProductComponentDeps {

    val context: Context
    val retrofit: Retrofit
    val buildConfig: AppBuildConfig

}