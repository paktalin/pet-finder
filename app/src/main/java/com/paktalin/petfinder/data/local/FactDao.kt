package com.paktalin.petfinder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.paktalin.petfinder.data.local.entity.FactEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM FactEntity")
    fun observeFacts(): Flow<List<FactEntity>>
}
