package com.android.detail.state

import com.android.common.model.PokemonDetailModel


sealed class PokemonDetailUiState {
    data object Loading : PokemonDetailUiState()
    data class Success(val pokemon: PokemonDetailModel) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}
