package com.paktalin.petfinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String?,
    val pictureUrl: String?,
    val type: String,
    val gender: GenderEntity
)

enum class GenderEntity {
    FEMALE, MALE, UNKNOWN
}
