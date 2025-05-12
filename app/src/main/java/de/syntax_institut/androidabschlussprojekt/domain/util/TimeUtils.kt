package de.syntax_institut.androidabschlussprojekt.domain.util



import java.util.concurrent.TimeUnit

fun formatTime(ms: Int): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(ms.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) % 60
    return "%02d:%02d".format(minutes, seconds)
}