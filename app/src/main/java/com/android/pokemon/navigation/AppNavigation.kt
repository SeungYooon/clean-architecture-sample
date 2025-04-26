package com.android.pokemon.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.core.component.BottomNavigationBar
import com.android.core.navigation.BottomNavItem
import com.android.detail.DetailViewModel
import com.android.favorite.FavoriteViewModel
import com.android.list.PokemonListViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val listViewModel: PokemonListViewModel = hiltViewModel()
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val detailViewModel: DetailViewModel = hiltViewModel()

    val bottomNavItems = listOf(BottomNavItem.List, BottomNavItem.Favorites)
    val bottomBarRoutes = bottomNavItems.map { it.route }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val shouldShowBottomBar by remember(currentRoute) {
        derivedStateOf {
            bottomBarRoutes.any { route ->
                currentRoute?.startsWith(route) == true
            }
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = shouldShowBottomBar,
                enter = fadeIn(animationSpec = tween(150)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                BottomNavigationBar(navController = navController, items = bottomNavItems)
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            listViewModel = listViewModel,
            favoriteViewModel = favoriteViewModel,
            detailViewModel = detailViewModel,
            innerPadding
        )
    }
}
