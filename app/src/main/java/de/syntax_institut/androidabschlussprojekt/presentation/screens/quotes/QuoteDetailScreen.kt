package de.syntax_institut.androidabschlussprojekt.presentation.screens.quotes

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.OceanBlue

@Composable
fun QuoteDetailScreen(
    quoteText: String,
    quoteAuthor: String
) {
    val context = LocalContext.current
    val wikiUrl = "https://en.wikipedia.org/wiki/${Uri.encode(quoteAuthor)}"

    val authorImages = mapOf(
        "buddha" to R.drawable.buddha,
        "plato" to R.drawable.plato,
        "aristotle" to R.drawable.aristotle
    )

    val authorKey = quoteAuthor.lowercase().trim()
    val imageRes = authorImages[authorKey] ?: R.drawable.default_author

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = quoteAuthor,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            Text(
                text = "„$quoteText“",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                color = ElegantRed,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "- $quoteAuthor",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = OceanBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl))
                        context.startActivity(intent)
                    }
                    .padding(horizontal = 8.dp)
            )

            Text(
                text = stringResource(R.string.author_info_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        // Spacer + Sona-Logo
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_sona),
                contentDescription = "Sona Logo",
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
            )
        }
    }
}