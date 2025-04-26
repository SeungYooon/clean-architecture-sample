package com.android.data.datasource

import com.android.data.api.PokemonApiService
import com.android.data.model.PokemonDetailResponse
import com.android.data.model.PokemonResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val pokemonApiService: PokemonApiService) {

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonResponse {
        return pokemonApiService.getPokemonList(limit, offset)
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse {
        return pokemonApiService.getPokemonDetail(id)
    }
}