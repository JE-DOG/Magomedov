package ru.je_dog.core.network.adapter

interface HttpResponseDescription {

    val statusCode: Int
    val statusMessage: String?

}