package de.syntax_institut.androidabschlussprojekt.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ElegantRed,
    secondary = OceanBlue,
    background = AppBackground,
    surface = AppBackground,
    onPrimary = VintageWhite,
    onSecondary = VintageWhite,
    onBackground = NobleBlack,
    onSurface = NobleBlack
)

private val DarkColorScheme = darkColorScheme(
    primary = ElegantRed,
    secondary = SoftPurple,
    background = NobleBlack,
    surface = NobleBlack,
    onPrimary = VintageWhite,
    onSecondary = VintageWhite,
    onBackground = VintageWhite,
    onSurface = VintageWhite
)

@Composable
fun SonaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}