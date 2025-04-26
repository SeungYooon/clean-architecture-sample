package com.android.data.datasource

import com.android.data.database.dao.PokemonDao
import com.android.data.database.model.PokemonDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao
) {
    suspend fun insertFavorite(pokemon: PokemonDbEntity) {
        pokemonDao.insert(pokemon)
    }

    fun getAllFavorites(): Flow<List<PokemonDbEntity>> {
        return pokemonDao.getAllFavorites()
    }

    suspend fun removeFavorite(pokemonId: Int) {
        pokemonDao.removeFavorite(pokemonId)
    }

    suspend fun getPokemonById(pokemonId: Int): PokemonDbEntity? {
        return pokemonDao.getById(pokemonId)
    }
}
