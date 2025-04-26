package com.android.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonDbEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val height: Int? = null,             // optional로 둬도 됨
    val weight: Int? = null,
    val types: List<String>? = null
)
