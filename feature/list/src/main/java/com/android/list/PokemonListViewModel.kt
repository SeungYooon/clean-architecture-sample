package com.android.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.android.core.mapper.PokemonMapper
import com.android.core.model.PokemonModel
import com.android.core.util.FavoriteStatusUpdater
import com.android.domain.usecase.GetFavoriteListUseCase
import com.android.domain.usecase.GetPokemonPagingUseCase
import com.android.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonPagingUseCase: GetPokemonPagingUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase
) : ViewModel(), FavoriteStatusUpdater {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState

    private val _favoriteIds = MutableStateFlow<List<Int>>(emptyList())

    private val _pokemonPagingData = MutableStateFlow<PagingData<PokemonModel>>(PagingData.empty())
    val pokemonPagingData: StateFlow<PagingData<PokemonModel>> get() = _pokemonPagingData

    private val pokemonPagingDataFlow: Flow<PagingData<PokemonModel>> =
        getPokemonPagingUseCase()
            .map { pagingData ->
                pagingData.map { entity ->
                    val isFavorite = _favoriteIds.value.contains(entity.id)
                    val updatedEntity = entity.copy(isFavorite = isFavorite)
                    PokemonMapper.mapToUIModel(updatedEntity)
                }
            }
            .cachedIn(viewModelScope)

    init {
        observeFavoriteList()
        collectPagingData()
    }

    private fun observeFavoriteList() {
        viewModelScope.launch {
            try {
                getFavoriteListUseCase().collectLatest { favoriteList ->
                    _favoriteIds.value = favoriteList.map { it.id }
                }
            } catch (e: Exception) {
                _uiState.value = PokemonListUiState.Error("즐겨찾기 목록을 불러올 수 없어요.")
            }
        }
    }

    private fun collectPagingData() {
        viewModelScope.launch {
            pokemonPagingDataFlow
                .onStart {
                    _uiState.value = PokemonListUiState.Loading
                }
                .catch {
                    _uiState.value = PokemonListUiState.Error("포켓몬 데이터를 불러오는 중 오류가 발생했어요.")
                }
                .collect { pagingData ->
                    _pokemonPagingData.value = pagingData
                    _uiState.value = PokemonListUiState.Success()
                }
        }
    }

    fun toggleFavorite(pokemon: PokemonModel) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(PokemonMapper.mapToEntity(pokemon))
                updateFavoriteIds(pokemon.id, !pokemon.isFavorite)
                refreshPokemonFavoriteStatus()
            } catch (e: Exception) {
                _uiState.value = PokemonListUiState.Error("즐겨찾기 변경에 실패했어요.")
            }
        }
    }

    override fun updatePokemonFavoriteStatus(pokemonId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteIds(pokemonId, isFavorite)
            refreshSinglePokemonFavoriteStatus(pokemonId, isFavorite)
        }
    }

    private fun updateFavoriteIds(pokemonId: Int, isFavorite: Boolean) {
        _favoriteIds.value = if (isFavorite) {
            _favoriteIds.value + pokemonId
        } else {
            _favoriteIds.value - pokemonId
        }
    }

    private fun refreshPokemonFavoriteStatus() {
        viewModelScope.launch {
            _pokemonPagingData.value = _pokemonPagingData.value.map { pokemon ->
                pokemon.copy(isFavorite = _favoriteIds.value.contains(pokemon.id))
            }
        }
    }

    private fun refreshSinglePokemonFavoriteStatus(pokemonId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            _pokemonPagingData.value = _pokemonPagingData.value.map { pokemon ->
                if (pokemon.id == pokemonId) {
                    pokemon.copy(isFavorite = isFavorite)
                } else {
                    pokemon
                }
            }
        }
    }
}
