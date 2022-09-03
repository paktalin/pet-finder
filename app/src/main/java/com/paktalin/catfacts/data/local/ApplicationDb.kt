package com.paktalin.catfacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paktalin.catfacts.data.local.entity.FactEntity

@Database(
    entities = [FactEntity::class],
    version = 1
)
abstract class ApplicationDb : RoomDatabase() {

    abstract fun factDao(): FactDao
}
