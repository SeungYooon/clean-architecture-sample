package com.android.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    data object List : BottomNavItem("목록", Icons.AutoMirrored.Filled.List, "list")
    data object Favorites : BottomNavItem("즐겨찾기", Icons.Default.Favorite, "favorite")
}
