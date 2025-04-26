package com.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.data.datasource.RemoteDataSource
import com.android.data.mapper.PokemonMapper
import com.android.data.paging.PokemonPagingSource
import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.entity.PokemonEntity
import com.android.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PokemonRepository {

    override fun getPagedPokemonList(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(remoteDataSource) }
        ).flow
    }

    override suspend fun getPokemonDetail(id: Int): PokemonDetailEntity {
        val response = remoteDataSource.getPokemonDetail(id)
        return PokemonMapper.mapToDomain(response)
    }
}