package com.android.core.navigation

sealed class NavItem(val screenRoute: String) {
    data object List : NavItem("list")
    data object Favorites : NavItem("favorites")
    data object Detail : NavItem("detail")
}
