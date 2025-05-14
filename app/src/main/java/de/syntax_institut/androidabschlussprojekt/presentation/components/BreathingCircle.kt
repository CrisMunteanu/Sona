package de.syntax_institut.androidabschlussprojekt.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple

@Composable
fun BreathingCircleWithLogo(
    animatedScale: Float,
    logoRes: Int
) {
    Box(
        modifier = Modifier
            .size(200.dp * animatedScale)
            .shadow(16.dp, shape = CircleShape)
            .background(Color.Transparent, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = NobleBlack,
                    shape = CircleShape
                )
        )


        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Sona Logo",
            modifier = Modifier
                .size(170.dp * animatedScale)
                .clip(CircleShape)
        )
    }
}