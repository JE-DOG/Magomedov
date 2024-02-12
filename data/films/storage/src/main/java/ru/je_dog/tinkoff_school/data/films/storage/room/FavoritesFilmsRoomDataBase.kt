package ru.je_dog.tinkoff_school.data.films.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.je_dog.tinkoff_school.core.data.storage.model.FilmEntity

@Database(
    entities = [
        FilmEntity::class
    ],
    version = 2
)
abstract class FavoritesFilmsRoomDataBase: RoomDatabase() {

    abstract fun favoritesFilmsDao(): FavoritesFilmsDao

}

