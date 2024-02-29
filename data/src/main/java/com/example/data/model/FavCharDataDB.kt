package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_chars")
data class FavCharDataDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val species: String,
    val gender: String,
    val origin: String,
    val image: String
)