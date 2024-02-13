package ru.je_dog.tinkoff_school.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequst = chain.request()
            .newBuilder().apply {
                addHeader("X-API-KEY","e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")

            }
            .build()

        return chain.proceed(newRequst)
    }
}

class CacheInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.MINUTES)
            .build()
        val newRequst = chain.request()
            .newBuilder().apply {
                header("Cache-Control", cacheControl.toString())
            }
            .build()

        return chain.proceed(newRequst)
    }
}

class ForceCacheInterceptor(
    private val context: Context
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val network = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = network.activeNetworkInfo
        val isNetworkAvailable = networkInfo?.isConnected == true
        val newRequst = chain.request()
            .newBuilder().apply {
                if (!isNetworkAvailable){
                    cacheControl(CacheControl.FORCE_CACHE)
                }
            }
            .build()

        return chain.proceed(newRequst)
    }

}