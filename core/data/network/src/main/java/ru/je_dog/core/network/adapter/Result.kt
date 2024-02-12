package ru.je_dog.core.network.adapter

import retrofit2.HttpException

sealed interface Result<out T> {

    sealed class Success<T>: Result<T> {

        abstract val value: T

        class Value<T>(override  val value: T): Success<T>()

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String?,
        ): Success<T>(), HttpResponseDescription

        object Empty: Success<Nothing>() {

            override val value: Nothing
                get() = error("No value")

        }

    }

    sealed class Failure<E: Throwable>(open val error: E? = null): Result<Nothing> {

        class Error(override val error: Throwable?): Failure<Throwable>(error = error)

        class HttpError(
            override val error: HttpException
        ): Failure<HttpException>(), HttpResponseDescription {

            override val statusCode: Int = error.code()

            override val statusMessage: String = error.message()

        }

    }

}