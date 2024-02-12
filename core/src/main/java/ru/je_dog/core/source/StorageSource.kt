package ru.je_dog.core.source

interface StorageSource<T>: Source<T> {

    suspend fun saveAll(items: List<T>)

}