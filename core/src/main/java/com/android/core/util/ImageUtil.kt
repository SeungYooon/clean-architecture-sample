package com.android.core.util

// ImageUtil.kt
fun buildPokemonImageUrl(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}
