package com.example.eletriccarapps.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector, val screen_route: String) {
    object Car : BottomNavItem("Home", Icons.Filled.Home, "car")
    object Favorite : BottomNavItem("Favoritos", Icons.Filled.Favorite, "favorite")
}
