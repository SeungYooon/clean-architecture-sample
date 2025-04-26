package com.android.pokemon.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.core.navigation.BottomNavItem
import com.android.core.navigation.DetailNav
import com.android.detail.DetailScreen
import com.android.detail.DetailViewModel
import com.android.favorite.FavoriteListScreen
import com.android.favorite.FavoriteViewModel
import com.android.list.PokemonListScreen
import com.android.list.PokemonListViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController,
    listViewModel: PokemonListViewModel,
    favoriteViewModel: FavoriteViewModel,
    detailViewModel: DetailViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.List.route
    ) {
        composable(BottomNavItem.List.route) {
            PokemonListScreen(navController, listViewModel)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoriteListScreen(navController, favoriteViewModel)
        }
        composable("${DetailNav.ROUTE}/{${DetailNav.ARG_ID}}/{${DetailNav.ARG_IS_FAVORITE}}") { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString(DetailNav.ARG_ID)?.toInt() ?: return@composable
            val isFavorite = backStackEntry.arguments?.getString(DetailNav.ARG_IS_FAVORITE)?.toBoolean() ?: false

            DetailScreen(
                navController,
                detailViewModel,
                listViewModel,
                pokemonId,
                isFavorite
            )
        }
    }
}
