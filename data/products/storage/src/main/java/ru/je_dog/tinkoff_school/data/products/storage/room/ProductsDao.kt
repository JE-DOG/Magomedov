package ru.je_dog.tinkoff_school.data.products.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.je_dog.storage.model.ProductEntity

@Dao
interface ProductsDao {

    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addProducts(productEntity: List<ProductEntity>)

    @Query("SELECT * FROM ${ProductEntity.TABLE_NAME}")
    fun getProducts(): List<ProductEntity>

}