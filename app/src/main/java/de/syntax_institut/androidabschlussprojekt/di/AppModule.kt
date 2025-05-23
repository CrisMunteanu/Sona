package de.syntax_institut.androidabschlussprojekt.di

import androidx.room.Room
import de.syntax_institut.androidabschlussprojekt.data.local.AppDatabase
import de.syntax_institut.androidabschlussprojekt.data.repository.*
import de.syntax_institut.androidabschlussprojekt.domain.remote.client.QuoteApiClient
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Room-Datenbank
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "sona_db"
        )
            .fallbackToDestructiveMigration() // Für dev-Phase
            .build()
    }

    // DAOs
    single { get<AppDatabase>().favoriteDao() }
    single { get<AppDatabase>().journalDao() }
    single { get<AppDatabase>().favoriteQuoteDao() }
    single { get<AppDatabase>().meditationHistoryDao() } // ✅ NEU

    // API-Clients
    single { QuoteApiClient(context = androidContext()) }

    // Repositories
    single { QuoteRepository(client = get()) }
    single { ZenQuotesRepository() }
    single { FavoritesRepository(get()) }
    single { JournalRepository(get()) }
    single { FavoriteQuoteRepository(get()) }
    single { MeditationHistoryRepository(get()) } // ✅ NEU

    // ViewModels
    viewModel {
        MainViewModel(
            favoritesRepository = get(),
            quoteRepository = get(),
            zenQuotesRepository = get()
        )
    }

    viewModel {
        JournalViewModel(journalRepository = get())
    }

    viewModel {
        FavoriteQuoteViewModel(repository = get())
    }

    viewModel {
        MeditationHistoryViewModel(repository = get()) // ✅ NEU
    }
}