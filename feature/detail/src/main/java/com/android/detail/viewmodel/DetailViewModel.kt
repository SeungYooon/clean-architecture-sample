package com.android.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.common.mapper.PokemonMapper
import com.android.detail.state.PokemonDetailUiState
import com.android.domain.usecase.AddDetailFavoriteUseCase
import com.android.domain.usecase.AddFavoriteUseCase
import com.android.domain.usecase.GetFavoriteListUseCase
import com.android.domain.usecase.GetFavoritePokemonDetailUseCase
import com.android.domain.usecase.GetPokemonDetailUseCase
import com.android.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val addDetailFavoriteUseCase: AddDetailFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val getFavoritePokemonDetailUseCase: GetFavoritePokemonDetailUseCase
) : ViewModel() {

    private val _pokemonState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val pokemonState: StateFlow<PokemonDetailUiState> = _pokemonState

    fun loadPokemonDetail(pokemonId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            _pokemonState.value = PokemonDetailUiState.Loading
            try {
                val pokemonDetail = getPokemonDetailUseCase(pokemonId)

                _pokemonState.value = PokemonDetailUiState.Success(
                    PokemonMapper.mapToUIDetailModel(pokemonDetail)
                )

                if (isFavorite) {
                    addDetailFavoriteUseCase.invoke(pokemonDetail)
                }
            } catch (e: Exception) {
                if (isFavorite) {
                    try {
                        val localDetail = getFavoritePokemonDetailUseCase(pokemonId)
                        _pokemonState.value = PokemonDetailUiState.Success(
                            PokemonMapper.mapToUIDetailModel(localDetail)
                        )
                    } catch (fallbackEx: Exception) {
                        _pokemonState.value = PokemonDetailUiState.Error("데이터를 불러오는 데 실패했습니다.")
                    }
                } else {
                    _pokemonState.value = PokemonDetailUiState.Error("네트워크 연결에 실패했습니다.")
                }
            }
        }
    }

    fun addToFavorites(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val current =
                    (_pokemonState.value as? PokemonDetailUiState.Success)?.pokemon ?: return@launch
                val currentFavorites = getFavoriteListUseCase().first()

                when {
                    currentFavorites.any { it.id == current.id } -> {
                        onFailure("이미 즐겨찾기에 추가된 포켓몬입니다.")
                    }

                    currentFavorites.size >= 10 -> {
                        onFailure("즐겨찾기는 최대 10개까지 저장할 수 있습니다.")
                    }

                    else -> {
                        val pokemonModel = PokemonMapper.mapDetailToModel(current)
                        addFavoriteUseCase(PokemonMapper.mapToEntity(pokemonModel))
                        onSuccess("즐겨찾기에 추가되었습니다.")
                    }
                }
            } catch (e: Exception) {
                onFailure("즐겨찾기 추가에 실패했습니다.")
            }
        }
    }

    fun removeFromFavorites(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val current =
                (_pokemonState.value as? PokemonDetailUiState.Success)?.pokemon ?: return@launch
            try {
                removeFavoriteUseCase(current.id)
                onSuccess()
            } catch (e: Exception) {
                onFailure("즐겨찾기 삭제에 실패했습니다.")
            }
        }
    }
}
