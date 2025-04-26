package com.android.domain.repository

import androidx.paging.PagingData
import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPagedPokemonList(): Flow<PagingData<PokemonEntity>>
    suspend fun getPokemonDetail(id: Int): PokemonDetailEntity
}
