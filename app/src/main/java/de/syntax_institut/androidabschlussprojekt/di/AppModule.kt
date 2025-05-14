package de.syntax_institut.androidabschlussprojekt.di

import androidx.room.Room
import de.syntax_institut.androidabschlussprojekt.data.local.AppDatabase
import de.syntax_institut.androidabschlussprojekt.data.repository.FavoritesRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.QuoteRepository
import de.syntax_institut.androidabschlussprojekt.domain.remote.client.QuoteApiClient
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // API-Client für type.fit mit Fallback auf lokale JSON-Datei
    single { QuoteApiClient(context = androidContext()) }

    // Zitat-Repository
    single { QuoteRepository(client = get()) }

    // Room-Datenbank
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "sona_db"
        ).fallbackToDestructiveMigration().build()
    }

    // DAO & FavoritesRepository
    single { get<AppDatabase>().favoriteDao() }
    single { FavoritesRepository(get()) }

    // ViewModel
    viewModel { MainViewModel(get(), get()) }
}