package ru.je_dog.core.network.adapter.factory

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import ru.je_dog.core.network.adapter.Result

class ResultCall<T>(
    private val call: Call<T>
): Call<Result<T>> {

    override fun clone(): Call<Result<T>> {
        return ResultCall(call.clone())
    }

    override fun execute(): Response<Result<T>> {
        throw NotImplementedError()
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        val resultCallback = ResultCallback(this,callback)
        call.enqueue(resultCallback)
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }

    class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<Result<T>>
    ): Callback<T>{

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: Result<T> = if (response.isSuccessful){
                Result.Success.HttpResponse(
                    value = response.body() as T,
                    statusCode = response.code(),
                    statusMessage = response.message()
                )
            }else {
                Result.Failure.HttpError(
                    HttpException(
                        response
                    )
                )
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = when(error) {
                is HttpException -> {
                    Result.Failure.HttpError(
                        error
                    )
                }

                else -> Result.Failure.Error(error)
            }

            callback.onResponse(proxy, Response.success(result))
        }

    }

}