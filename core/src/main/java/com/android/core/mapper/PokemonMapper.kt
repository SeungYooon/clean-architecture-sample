package com.android.core.mapper

import com.android.core.model.PokemonDetailModel
import com.android.core.model.PokemonModel
import com.android.core.util.buildPokemonImageUrl
import com.android.domain.entity.PokemonDetailEntity
import com.android.domain.entity.PokemonEntity

object PokemonMapper {

    fun mapToUIModel(pokemonEntity: PokemonEntity): PokemonModel {
        return PokemonModel(
            id = pokemonEntity.id,
            name = pokemonEntity.name,
            imageUrl = pokemonEntity.imageUrl,
            isFavorite = pokemonEntity.isFavorite
        )
    }

    fun mapToUIModelList(pokemonEntities: List<PokemonEntity>): List<PokemonModel> {
        return pokemonEntities.map { mapToUIModel(it) }
    }

    fun mapToEntity(pokemonModel: PokemonModel): PokemonEntity {
        return PokemonEntity(
            id = pokemonModel.id,
            name = pokemonModel.name,
            imageUrl = pokemonModel.imageUrl,
            isFavorite = pokemonModel.isFavorite
        )
    }

    fun mapToDetailEntity(pokemonDetailModel: PokemonDetailModel): PokemonDetailEntity {
        return PokemonDetailEntity(
            id = pokemonDetailModel.id,
            name = pokemonDetailModel.name,
            weight = pokemonDetailModel.weight,
            height = pokemonDetailModel.height,
            types = pokemonDetailModel.types,
            imageUrl = pokemonDetailModel.imageUrl
        )
    }

    fun mapToUIDetailModel(pokemonDetailEntity: PokemonDetailEntity): PokemonDetailModel {
        return PokemonDetailModel(
            id = pokemonDetailEntity.id,
            name = pokemonDetailEntity.name,
            height = pokemonDetailEntity.height,
            weight = pokemonDetailEntity.weight,
            types = pokemonDetailEntity.types,
            imageUrl = pokemonDetailEntity.imageUrl
        )
    }

    fun mapDetailToModel(detail: PokemonDetailModel): PokemonModel {
        return PokemonModel(
            id = detail.id,
            name = detail.name,
            imageUrl = buildPokemonImageUrl(detail.id),
            isFavorite = true
        )
    }
}
