package de.syntax_institut.androidabschlussprojekt.di

import androidx.room.Room
import de.syntax_institut.androidabschlussprojekt.data.local.AppDatabase
import de.syntax_institut.androidabschlussprojekt.data.repository.FavoritesRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.JournalRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.QuoteRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ZenQuotesRepository
import de.syntax_institut.androidabschlussprojekt.domain.remote.client.QuoteApiClient
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.JournalViewModel
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
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
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    single { get<AppDatabase>().favoriteDao() }
    single { get<AppDatabase>().journalDao() }

    // API-Client für type.fit (Quote Gallery)
    single { QuoteApiClient(context = androidContext()) }

    // Repositories
    single { QuoteRepository(client = get()) }
    single { ZenQuotesRepository() }
    single { FavoritesRepository(get()) }
    single { JournalRepository(get()) }

    // ViewModels
    viewModel {
        MainViewModel(
            favoritesRepository = get(),
            quoteRepository = get(),
            zenQuotesRepository = get()
        )
    }

    viewModel {
        JournalViewModel(
            journalRepository = get()
        )
    }
}