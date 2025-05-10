package com.android.list.state

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val message: String = "") : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}
