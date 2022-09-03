package com.paktalin.petfinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FactEntity(
    @PrimaryKey val id: String,
    val fact: String
)
