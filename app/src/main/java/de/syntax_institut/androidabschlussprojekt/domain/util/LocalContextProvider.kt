package de.syntax_institut.androidabschlussprojekt.domain.util



import android.content.Context
import androidx.compose.runtime.compositionLocalOf

val LocalLocalizedContext = compositionLocalOf<Context> {
    error("LocalizedContext not provided")
}