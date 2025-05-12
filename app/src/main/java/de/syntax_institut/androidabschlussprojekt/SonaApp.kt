package de.syntax_institut.androidabschlussprojekt


import android.app.Application
import de.syntax_institut.androidabschlussprojekt.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import de.syntax_institut.androidabschlussprojekt.BuildConfig

class SonaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@SonaApp)


            printLogger(Level.INFO)





            modules(appModule)
        }
    }
}