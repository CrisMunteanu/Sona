package de.syntax_institut.androidabschlussprojekt.domain.util

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import java.util.*

fun setLocaleAndRestart(activity: Activity, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    activity.resources.updateConfiguration(config, activity.resources.displayMetrics)

    val intent = Intent(activity, activity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    activity.finish()
    activity.startActivity(intent)
}