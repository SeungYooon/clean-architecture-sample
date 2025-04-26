package com.android.domain.entity

data class PokemonEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)
