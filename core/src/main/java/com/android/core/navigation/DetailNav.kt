package com.android.core.navigation

object DetailNav {
    const val ROUTE = "detail"
    const val ARG_ID = "pokemonId"
    const val ARG_IS_FAVORITE = "isFavorite"

    fun routeWithArgs(id: Int, isFavorite: Boolean): String {
        return "$ROUTE/$id/$isFavorite"
    }
}
