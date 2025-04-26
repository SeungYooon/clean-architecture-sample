package com.android.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.data.datasource.RemoteDataSource
import com.android.data.mapper.PokemonMapper
import com.android.domain.entity.PokemonEntity

class PokemonPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, PokemonEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonEntity> {
        return try {
            val page = params.key ?: 0
            val response = remoteDataSource.getPokemonList(limit = 30, offset = page * 30)

            // PokemonItem을 PokemonEntity로 변환
            val pokemonEntities = response.results.map { pokemonItem ->
                PokemonMapper.mapToDomain(pokemonItem) // 변환
            }

            LoadResult.Page(
                data = pokemonEntities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonEntity>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(30) ?: anchorPage?.nextKey?.minus(30)
        }
    }
}
