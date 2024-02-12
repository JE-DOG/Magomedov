package ru.je_dog.tinkoff_school.data.products.storage.room

import android.content.Context
import androidx.room.Room
import ru.je_dog.storage.room.RoomDataBaseFactory

abstract class ProductsRoomDataBaseFactory(
    context: Context,
    name: String
): RoomDataBaseFactory<ProductsRoomDataBase> {

    override val roomDatabase: ProductsRoomDataBase = Room.databaseBuilder(
        context,
        ProductsRoomDataBase::class.java,
        name
    ).build()

    class Base(
        context: Context
    ): ProductsRoomDataBaseFactory(context, "database")

    class Mock(
        context: Context
    ): ProductsRoomDataBaseFactory(context, "mock_database")

}