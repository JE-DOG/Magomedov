package ru.je_dog.tinkoff_school.core.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.je_dog.core.model.CountryDomain

class CountryEntity(
    override val country: String
): CountryDomain()