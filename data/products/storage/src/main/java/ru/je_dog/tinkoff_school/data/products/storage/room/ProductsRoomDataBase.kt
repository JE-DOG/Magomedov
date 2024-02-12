package ru.je_dog.tinkoff_school.data.products.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.je_dog.storage.model.ProductEntity

@Database(
    entities = [
        ProductEntity::class
    ],
    version = 1
)
abstract class ProductsRoomDataBase: RoomDatabase() {

    abstract fun productsDao(): ProductsDao

}

