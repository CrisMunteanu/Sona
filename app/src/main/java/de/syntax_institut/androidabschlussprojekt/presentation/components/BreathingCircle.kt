package de.syntax_institut.androidabschlussprojekt.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer


@Composable
fun BreathingCircleWithLogo(
    animatedScale: Float,
    logoRes: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer(
                scaleX = animatedScale,
                scaleY = animatedScale
            )
    ) {
        // Luftiger Kreis mit transparenter Farbe
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFF692621).copy(alpha = 0.4f),
                style = Stroke(width = 4f)
            )
        }

        // Logo zentriert
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = null,
            modifier = Modifier.size(140.dp)
        )
    }
}