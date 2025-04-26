package com.android.domain.usecase

import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): PokemonDetailEntity =
        pokemonRepository.getPokemonDetail(id)
}
