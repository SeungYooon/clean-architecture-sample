package com.android.domain.usecase

import androidx.paging.PagingData
import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonPagingUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<PokemonEntity>> = pokemonRepository.getPagedPokemonList()
}
