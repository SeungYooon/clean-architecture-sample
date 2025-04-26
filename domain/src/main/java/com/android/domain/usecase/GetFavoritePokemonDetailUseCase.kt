package com.android.domain.usecase

import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoritePokemonDetailUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(id: Int): PokemonDetailEntity =
        repository.getFavoriteById(id)
}
