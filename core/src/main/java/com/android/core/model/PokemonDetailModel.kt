package com.android.core.model

// PokemonDetailModel.kt
data class PokemonDetailModel(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean = false
)
