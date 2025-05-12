package de.syntax_institut.androidabschlussprojekt.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import de.syntax_institut.androidabschlussprojekt.presentation.navigation.BottomNavItem
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = VintageWhite,
        tonalElevation = 2.dp
    ) {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Favorites,
            BottomNavItem.Poses,
            BottomNavItem.Settings // ✅ NEU
        ).forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(BottomNavItem.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(28.dp),
                        tint = if (isSelected) ElegantRed else NobleBlack
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) ElegantRed else Color.Gray
                    )
                }
            )
        }
    }
}