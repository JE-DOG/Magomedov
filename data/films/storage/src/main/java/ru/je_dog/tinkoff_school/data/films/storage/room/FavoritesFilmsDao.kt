package ru.je_dog.tinkoff_school.data.films.storage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.je_dog.tinkoff_school.core.data.storage.model.FilmEntity

@Dao
interface FavoritesFilmsDao {

    @Insert(entity = FilmEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(productEntity: FilmEntity)

    @Delete(entity = FilmEntity::class)
    suspend fun delete(productEntity: FilmEntity)

    @Query("SELECT * FROM ${FilmEntity.TABLE_NAME}")
    suspend fun getAll(): List<FilmEntity>

}