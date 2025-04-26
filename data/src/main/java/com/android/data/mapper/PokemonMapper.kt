package com.android.data.mapper

import com.android.core.util.buildPokemonImageUrl
import com.android.data.database.model.PokemonDbEntity
import com.android.data.model.PokemonDetailResponse
import com.android.data.model.PokemonResult
import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.entity.PokemonEntity

object PokemonMapper {
    fun mapToDomain(result: PokemonResult): PokemonEntity {
        val id = result.url.trimEnd('/').split("/").last().toInt()
        val imageUrl = buildPokemonImageUrl(id)
        return PokemonEntity(
            id = id,
            name = result.name,
            imageUrl = imageUrl
        )
    }

    fun mapDetailToDomain(response: PokemonDetailResponse): PokemonDetailEntity {
        return PokemonDetailEntity(
            id = response.id,
            name = response.name,
            height = response.height,
            weight = response.weight,
            imageUrl = "",
            types = response.types.map { it.type.name }
        )
    }

    fun mapToDetailEntity(pokemonDbEntity: PokemonDbEntity):PokemonDetailEntity {
        return PokemonDetailEntity(
            id = pokemonDbEntity.id,
            name = pokemonDbEntity.name,
            imageUrl = pokemonDbEntity.imageUrl,
            isFavorite = pokemonDbEntity.isFavorite,
            weight = pokemonDbEntity.weight ?: 0,
            height = pokemonDbEntity.height ?: 0,
            types = pokemonDbEntity.types ?: emptyList()
        )
    }

    fun mapToDomain(response: PokemonDetailResponse): PokemonDetailEntity {
        return PokemonDetailEntity(
            id = response.id,
            name = response.name,
            height = response.height,
            weight = response.weight,
            types = response.types.map { it.type.name }, // types 안에 type.name 추출
            imageUrl = buildPokemonImageUrl(response.id),
            isFavorite = false // API에서 안주니까 기본값 false
        )
    }

    fun mapToDomain(dbEntity: PokemonDbEntity): PokemonEntity {
        return PokemonEntity(
            id = dbEntity.id,
            name = dbEntity.name,
            imageUrl = dbEntity.imageUrl,
            isFavorite = dbEntity.isFavorite
        )
    }

    fun mapToDbEntity(domainEntity: PokemonEntity): PokemonDbEntity {
        return PokemonDbEntity(
            id = domainEntity.id,
            name = domainEntity.name,
            imageUrl = domainEntity.imageUrl,
            isFavorite = domainEntity.isFavorite
        )
    }

    fun mapToDbEntity(domainDetailEntity: PokemonDetailEntity): PokemonDbEntity {
        return PokemonDbEntity(
            id = domainDetailEntity.id,
            name = domainDetailEntity.name,
            imageUrl = domainDetailEntity.imageUrl,
            isFavorite = domainDetailEntity.isFavorite,
            weight = domainDetailEntity.weight,
            height = domainDetailEntity.height,
            types = domainDetailEntity.types
        )
    }
}