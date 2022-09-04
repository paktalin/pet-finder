package com.paktalin.petfinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paktalin.petfinder.data.local.entity.PetEntity
import com.paktalin.petfinder.data.local.entity.PetTypeEntity

@Database(
    entities = [PetEntity::class, PetTypeEntity::class],
    version = 1
)
abstract class ApplicationDb : RoomDatabase() {

    abstract fun petDao(): PetDao

    abstract fun petTypeDao(): PetTypeDao
}
