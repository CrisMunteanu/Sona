package de.syntax_institut.androidabschlussprojekt.presentation.screens.info



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.components.BenefitItem

@Composable
fun MentalBenefitsDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Header-Bild
        Image(
            painter = painterResource(id = R.drawable.mental_benefits_header),
            contentDescription = stringResource(R.string.mental_benefits_title),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        // Titel
        Text(
            text = stringResource(R.string.mental_benefits_title),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            color = ElegantRed
        )

        // Vorteile (Liste)
        BenefitItem(
            title = stringResource(R.string.benefit_focus_title),
            description = stringResource(R.string.benefit_focus_desc)
        )
        BenefitItem(
            title = stringResource(R.string.benefit_stress_title),
            description = stringResource(R.string.benefit_stress_desc)
        )
        BenefitItem(
            title = stringResource(R.string.benefit_sleep_title),
            description = stringResource(R.string.benefit_sleep_desc)
        )
        BenefitItem(
            title = stringResource(R.string.benefit_awareness_title),
            description = stringResource(R.string.benefit_awareness_desc)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Navigation-Button
        Button(
            onClick = {
                navController.navigate("start") {
                    popUpTo("mental_benefits_detail") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.go_to_start))
        }
    }
}