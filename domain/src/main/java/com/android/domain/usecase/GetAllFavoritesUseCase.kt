package com.android.domain.usecase

import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): List<PokemonEntity> {
        return favoriteRepository.getAllFavorites().first() // Flow를 suspend 함수로 변환하여 첫 번째 값을 반환
    }
}
