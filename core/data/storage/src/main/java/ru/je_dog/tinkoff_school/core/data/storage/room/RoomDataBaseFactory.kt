package ru.je_dog.tinkoff_school.core.data.storage.room

import android.content.Context
import androidx.room.RoomDatabase

interface RoomDataBaseFactory<D: RoomDatabase> {

    val roomDatabase: D

}