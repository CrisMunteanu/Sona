package de.syntax_institut.androidabschlussprojekt.presentation.navigation



import de.syntax_institut.androidabschlussprojekt.R

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Start : Screen("start")
    object Home : Screen("home")
    object Favorites : Screen("favorites")

    data class Player(val fileName: String, val imageResId: Int) :
        Screen("player/$fileName/$imageResId") {
        companion object {
            const val route = "player/{fileName}/{imageResId}"
        }
    }
}