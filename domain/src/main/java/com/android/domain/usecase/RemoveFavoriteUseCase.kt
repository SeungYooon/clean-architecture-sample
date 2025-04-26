package com.android.domain.usecase

import com.android.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(pokemonId: Int) {
        repository.removeFavorite(pokemonId)
    }
}
