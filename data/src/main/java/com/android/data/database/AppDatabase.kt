package com.android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.data.database.dao.PokemonDao
import com.android.data.database.model.PokemonDbEntity
import com.android.data.util.Converters

@Database(entities = [PokemonDbEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}