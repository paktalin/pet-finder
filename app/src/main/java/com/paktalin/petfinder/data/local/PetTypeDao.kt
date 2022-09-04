package com.paktalin.petfinder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.paktalin.petfinder.data.local.entity.PetTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetTypeDao {

    @Transaction
    suspend fun replacePetTypes(petTypes: List<PetTypeEntity>) {
        deletePetTypes()
        insertPetTypes(petTypes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetTypes(entities: List<PetTypeEntity>)

    @Query("DELETE FROM PetTypeEntity")
    suspend fun deletePetTypes()

    @Query("SELECT * FROM PetTypeEntity")
    fun observePetTypes(): Flow<List<PetTypeEntity>>
}
