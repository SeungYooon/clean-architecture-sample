package com.android.domain.entity

data class PokemonDetailEntity(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean = false
)
