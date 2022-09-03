package com.paktalin.catfacts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.paktalin.catfacts.data.local.entity.FactEntity

@Dao
interface FactDao {

    @Transaction
    suspend fun replaceFacts(facts: List<FactEntity>) {
        deleteFacts()
        insertFacts(facts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacts(entities: List<FactEntity>)

    @Query("DELETE FROM FactEntity")
    suspend fun deleteFacts()
}
