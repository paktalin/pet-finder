package com.paktalin.petfinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paktalin.petfinder.data.local.entity.PetEntity

@Database(
    entities = [PetEntity::class],
    version = 1
)
abstract class ApplicationDb : RoomDatabase() {

    abstract fun petDao(): PetDao
}
