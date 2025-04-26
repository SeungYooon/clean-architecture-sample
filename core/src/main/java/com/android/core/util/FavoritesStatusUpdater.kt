package com.android.core.util

// FavoritesStatusUpdater.kt
interface FavoriteStatusUpdater {
    fun updatePokemonFavoriteStatus(pokemonId: Int, isFavorite: Boolean)
}
