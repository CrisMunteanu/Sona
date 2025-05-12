package de.syntax_institut.androidabschlussprojekt.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Favorites : BottomNavItem("favorites", Icons.Filled.Favorite, "Favoriten")
    object Poses : BottomNavItem("pose_list", Icons.Filled.SelfImprovement, "Posen")
    object Settings : BottomNavItem("settings", Icons.Filled.Settings, "Einstellungen")
}