package de.syntax_institut.androidabschlussprojekt.presentation.screens.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.presentation.components.BenefitItem
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentalBenefitsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.mental_benefits_title),
                        color = ElegantRed,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding_meditation),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = stringResource(R.string.benefit_intro),
                style = MaterialTheme.typography.bodyLarge
            )

            BenefitItem(
                title = stringResource(R.string.benefit_focus),
                description = stringResource(R.string.benefit_focus_desc)
            )
            BenefitItem(
                title = stringResource(R.string.benefit_stress),
                description = stringResource(R.string.benefit_stress_desc)
            )
            BenefitItem(
                title = stringResource(R.string.benefit_sleep),
                description = stringResource(R.string.benefit_sleep_desc)
            )
            BenefitItem(
                title = stringResource(R.string.benefit_emotions),
                description = stringResource(R.string.benefit_emotions_desc)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

