package com.android.data.repository

import com.android.data.datasource.LocalDataSource
import com.android.data.mapper.PokemonMapper
import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : FavoriteRepository {
    override suspend fun addFavorite(pokemon: PokemonEntity) {
        localDataSource.insertFavorite(PokemonMapper.mapToDbEntity(pokemon))
    }

    override suspend fun removeFavorite(pokemonId: Int) {
        localDataSource.removeFavorite(pokemonId)
    }

    override suspend fun addDetailFavorite(pokemonDetailEntity: PokemonDetailEntity) {
        localDataSource.insertFavorite(PokemonMapper.mapToDbEntity(pokemonDetailEntity))
    }

    override fun getAllFavorites(): Flow<List<PokemonEntity>> {
        return localDataSource.getAllFavorites().map { it.map { PokemonMapper.mapToDomain(it) } }
    }

    override suspend fun getFavoriteById(id: Int): PokemonDetailEntity {
        return localDataSource.getPokemonById(id)?.let {
            PokemonMapper.mapToDetailEntity(it)
        } ?: throw NoSuchElementException("포켓몬이 존재하지 않습니다. id=$id")
    }
}