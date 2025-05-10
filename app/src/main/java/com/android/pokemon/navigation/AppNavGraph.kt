package com.android.pokemon.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.core.navigation.BottomNavItem
import com.android.core.navigation.DetailNav
import com.android.detail.ui.DetailScreen
import com.android.detail.viewmodel.DetailViewModel
import com.android.favorite.ui.FavoriteListScreen
import com.android.favorite.viewmodel.FavoriteViewModel
import com.android.list.ui.PokemonListScreen
import com.android.list.viewmodel.PokemonListViewModel


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
            PokemonListScreen(navController, listViewModel, innerPadding)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoriteListScreen(navController, favoriteViewModel, innerPadding)
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
