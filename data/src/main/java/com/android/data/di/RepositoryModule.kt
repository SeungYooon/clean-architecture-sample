package com.android.data.di

import com.android.data.repository.FavoriteRepositoryImpl
import com.android.data.repository.PokemonRepositoryImpl
import com.android.domain.repository.FavoriteRepository
import com.android.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(repositoryImpl: PokemonRepositoryImpl): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(repositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}
