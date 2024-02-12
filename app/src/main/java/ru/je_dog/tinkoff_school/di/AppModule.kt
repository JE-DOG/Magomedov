package ru.je_dog.tinkoff_school.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.je_dog.core.app.AppBuildConfig
import ru.je_dog.core.network.adapter.factory.CustomAdapterFactory
import ru.je_dog.tinkoff_school.BuildConfig

@Module
class AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CustomAdapterFactory())
            .build()
    }

    @Provides
    fun provideAppBuildConfig(): AppBuildConfig {
        return object: AppBuildConfig {
            override val isDebug: Boolean = BuildConfig.DEBUG
        }
    }

}