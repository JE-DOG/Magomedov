package ru.je_dog.tinkoff_school.di

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.je_dog.core.app.AppBuildConfig
import ru.je_dog.core.network.adapter.factory.CustomAdapterFactory
import ru.je_dog.tinkoff_school.BuildConfig

@Module
class AppModule {

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            //This don't work((
//            .addNetworkInterceptor { chain ->
//                val request = chain.request()
//
//                request.newBuilder()
//                    .addHeader("X-API-KEY","e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
//                    .build()
//
//                chain.proceed(request)
//            }
            .build()
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