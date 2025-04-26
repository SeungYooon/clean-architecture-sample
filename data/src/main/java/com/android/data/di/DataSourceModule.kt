package com.android.data.di

import com.android.data.api.PokemonApiService
import com.android.data.database.dao.PokemonDao
import com.android.data.datasource.LocalDataSource
import com.android.data.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(pokemonApiService: PokemonApiService): RemoteDataSource {
        return RemoteDataSource(pokemonApiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(pokemonDao: PokemonDao): LocalDataSource {
        return LocalDataSource(pokemonDao)
    }
}