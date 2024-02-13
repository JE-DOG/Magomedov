package ru.je_dog.tinkoff_school.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.je_dog.core.app.AppBuildConfig
import ru.je_dog.core.network.adapter.factory.CustomAdapterFactory
import ru.je_dog.tinkoff_school.BuildConfig
import ru.je_dog.tinkoff_school.network.ApiKeyInterceptor
import ru.je_dog.tinkoff_school.network.CacheInterceptor
import ru.je_dog.tinkoff_school.network.ForceCacheInterceptor
import java.io.File

@Module
class AppModule {

    @Provides
    fun provideOkhttpClient(
        cache: Cache,
        context: Context
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .addInterceptor(ApiKeyInterceptor())
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ForceCacheInterceptor(context))
            .build()
    }

    @Provides
    fun provideNetworkCache(
        context: Context
    ): Cache {
        val cacheDir = File(context.cacheDir,"http-cache")
        val cacheSize = 10L * 1024L * 1024L
        return  Cache(cacheDir,cacheSize)
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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