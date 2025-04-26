package com.android.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("types")
    val types: List<PokemonTypeWrapper>
)

data class PokemonTypeWrapper(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: PokemonType
)

data class PokemonType(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)