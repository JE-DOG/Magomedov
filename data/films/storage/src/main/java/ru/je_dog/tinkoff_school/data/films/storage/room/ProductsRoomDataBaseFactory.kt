package ru.je_dog.tinkoff_school.data.films.storage.room

import android.content.Context
import androidx.room.Room
import ru.je_dog.tinkoff_school.core.data.storage.room.RoomDataBaseFactory

abstract class ProductsRoomDataBaseFactory(
    context: Context,
    name: String
): RoomDataBaseFactory<FavoritesFilmsRoomDataBase> {

    override val roomDatabase: FavoritesFilmsRoomDataBase = Room.databaseBuilder(
        context,
        FavoritesFilmsRoomDataBase::class.java,
        name
    ).build()

    class Base(
        context: Context
    ): ProductsRoomDataBaseFactory(context, "database")

    class Mock(
        context: Context
    ): ProductsRoomDataBaseFactory(context, "mock_database")

}