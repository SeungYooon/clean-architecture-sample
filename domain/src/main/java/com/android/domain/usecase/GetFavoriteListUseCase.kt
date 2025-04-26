package com.android.domain.usecase

import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<PokemonEntity>> =
        repository.getAllFavorites()
}
