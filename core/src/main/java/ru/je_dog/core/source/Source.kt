package ru.je_dog.core.source

interface Source<T> {

    suspend fun getAll(): List<T>

}