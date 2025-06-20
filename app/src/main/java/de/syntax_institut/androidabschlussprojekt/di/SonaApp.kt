package de.syntax_institut.androidabschlussprojekt.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

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