package com.android.data.api

import com.android.data.model.PokemonDetailResponse
import com.android.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    // 포켓몬 목록 조회
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponse

    // 포켓몬 상세 정보 조회
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetailResponse
}
