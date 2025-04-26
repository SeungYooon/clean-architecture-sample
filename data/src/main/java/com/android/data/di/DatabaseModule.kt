package com.android.data.di

import android.content.Context
import com.android.data.database.AppDatabase
import com.android.data.database.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providePokemonDao(database: AppDatabase): PokemonDao {
        return database.pokemonDao()
    }
}