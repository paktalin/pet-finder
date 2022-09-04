package com.paktalin.petfinder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.paktalin.petfinder.data.local.entity.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Transaction
    suspend fun replacePets(pets: List<PetEntity>) {
        deletePets()
        insertPets(pets)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(entities: List<PetEntity>)

    @Query("DELETE FROM PetEntity")
    suspend fun deletePets()

    @Query("SELECT * FROM PetEntity")
    fun observePets(): Flow<List<PetEntity>>

    @Query("SELECT * FROM PetEntity WHERE id=:id")
    fun observePet(id: Long): Flow<PetEntity?>
}
