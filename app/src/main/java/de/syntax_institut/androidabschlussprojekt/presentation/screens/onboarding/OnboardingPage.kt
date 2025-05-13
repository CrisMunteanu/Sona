package de.syntax_institut.androidabschlussprojekt.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import de.syntax_institut.androidabschlussprojekt.R

data class OnboardingPage(
    @DrawableRes val imageRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboarding_meditation,
        title = R.string.onboard_title_1,
        description = R.string.onboard_desc_1
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding_music,
        title = R.string.onboard_title_2,
        description = R.string.onboard_desc_2
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding_ready,
        title = R.string.onboard_title_3,
        description = R.string.onboard_desc_3
    )
)