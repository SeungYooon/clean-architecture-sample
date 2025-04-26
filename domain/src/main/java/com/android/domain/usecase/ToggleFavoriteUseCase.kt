package com.android.domain.usecase

import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(pokemon: PokemonEntity) {
        if (pokemon.isFavorite) {
            favoriteRepository.removeFavorite(pokemon.id)
        } else {
            favoriteRepository.addFavorite(pokemon.copy(isFavorite = true))
        }
    }
}
