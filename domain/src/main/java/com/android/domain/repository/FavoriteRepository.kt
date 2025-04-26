package com.android.domain.repository

import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addFavorite(pokemon: PokemonEntity)
    suspend fun removeFavorite(pokemonId: Int)
    suspend fun addDetailFavorite(pokemonDetailEntity: PokemonDetailEntity)
    suspend fun getFavoriteById(id: Int): PokemonDetailEntity
    fun getAllFavorites(): Flow<List<PokemonEntity>>
}
