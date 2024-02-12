package ru.je_dog.tinkoff_school.core.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.je_dog.core.model.GenreDomain

class GenreEntity(
    override val genre: String
): GenreDomain()
