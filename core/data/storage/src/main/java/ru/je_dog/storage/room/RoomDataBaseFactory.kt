package ru.je_dog.storage.room

import android.content.Context
import androidx.room.RoomDatabase

interface RoomDataBaseFactory<D: RoomDatabase> {

    val roomDatabase: D

}