package com.android.detail

import com.android.core.model.PokemonDetailModel

sealed class PokemonDetailUiState {
    data object Loading : PokemonDetailUiState()
    data class Success(val pokemon: PokemonDetailModel) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}
