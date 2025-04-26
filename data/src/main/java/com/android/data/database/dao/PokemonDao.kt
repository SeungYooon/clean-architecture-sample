package com.android.data.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.data.database.model.PokemonDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonDbEntity)

    @Query("SELECT * FROM pokemon_table WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<PokemonDbEntity>>

    @Query("DELETE FROM pokemon_table WHERE id = :pokemonId")
    suspend fun removeFavorite(pokemonId: Int)

    @Query("SELECT * FROM pokemon_table WHERE id = :pokemonId")
    suspend fun getById(pokemonId: Int): PokemonDbEntity?
}