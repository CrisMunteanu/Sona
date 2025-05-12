package de.syntax_institut.androidabschlussprojekt.domain.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale


fun Context.setAppLocale(languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration(resources.configuration)
    config.setLocale(locale)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        createConfigurationContext(config)
    } else {
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
        this
    }
}