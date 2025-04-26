package com.android.domain.usecase

import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(pokemon: PokemonEntity) {
        repository.addFavorite(pokemon.copy(isFavorite = true))
    }
}
