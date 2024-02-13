package ru.je_dog.core.network.adapter.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import ru.je_dog.core.network.adapter.Result

fun <T> Result<T>.asFailure(): Result.Failure<*> {
    return this as Result.Failure<*>
}

fun <T> Result<T>.asSuccess(): Result.Success<T> {
    return this as Result.Success<T>
}

fun <T> Result<T>.isSuccess(): Boolean {
    return this is Result.Success
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Result.Failure<*>)
    }
    return this is Result.Failure<*>
}

fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when(this) {
        is Result.Success -> Result.Success.Value(transform(value))
        is Result.Failure<*> -> this
    }
}

fun <T, R> Result<T>.flatMap(transform: (result: Result<T>) -> Result<R>): Result<R> {
    return transform(this)
}