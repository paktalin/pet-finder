package com.paktalin.petfinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetTypeEntity(@PrimaryKey val name: String)
