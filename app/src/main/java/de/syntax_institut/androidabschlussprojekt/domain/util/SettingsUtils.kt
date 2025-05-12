package de.syntax_institut.androidabschlussprojekt.domain.util


import android.content.Context
import android.content.Intent
import de.syntax_institut.androidabschlussprojekt.MainActivity
import java.util.Locale


fun setLocaleAndRestart(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val config = resources.configuration
    config.setLocale(locale)

    resources.updateConfiguration(config, resources.displayMetrics)

    //  App neu starten (über Intent)
    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)
}