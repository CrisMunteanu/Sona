package de.syntax_institut.androidabschlussprojekt.di

import androidx.room.Room
import de.syntax_institut.androidabschlussprojekt.data.local.AppDatabase
import de.syntax_institut.androidabschlussprojekt.data.remote.PixabayMusicApiService
import de.syntax_institut.androidabschlussprojekt.data.repository.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.remote.NasaApiService
import de.syntax_institut.androidabschlussprojekt.domain.remote.client.QuoteApiClient
import de.syntax_institut.androidabschlussprojekt.presentation.screens.buddhism.BuddhistTextViewModel
import de.syntax_institut.androidabschlussprojekt.presentation.screens.cosmic.CosmicMeditationViewModel
import de.syntax_institut.androidabschlussprojekt.presentation.screens.nasa.NasaPictureViewModel
import de.syntax_institut.androidabschlussprojekt.presentation.screens.yogaradio.YogaRadioViewModel



val appModule = module {

    // Room-Datenbank
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "sona_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    single { get<AppDatabase>().favoriteDao() }
    single { get<AppDatabase>().journalDao() }
    single { get<AppDatabase>().favoriteQuoteDao() }
    single { get<AppDatabase>().meditationHistoryDao() }

    // API-Clients
    single { QuoteApiClient(context = androidContext()) }

    // Pixabay API Setup
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PixabayMusicApiService> {
        get<Retrofit>().create(PixabayMusicApiService::class.java)
    }

    // Repositories
    single { QuoteRepository(client = get()) }
    single { ZenQuotesRepository() }
    single { FavoritesRepository(get()) }
    single { JournalRepository(get()) }
    single { FavoriteQuoteRepository(get()) }
    single { MeditationHistoryRepository(get()) }
    single {
        val apiKey: String = BuildConfig.PIXABAY_API_KEY
        PixabayMusicRepository(
            pixabayMusicApiService = get(),
            apiKey = apiKey
        )
    }

    single<NasaApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }

    single<NasaRepository> {
        val apiKey = BuildConfig.NASA_API_KEY
        NasaRepositoryImpl(
            apiService = get(),
            apiKey = apiKey
        )
    }
    single { YogaRadioViewModel() }


    single { BuddhistTextViewModel() }

    // ViewModels
    viewModel {
        MainViewModel(
            favoritesRepository = get(),
            quoteRepository = get(),
            zenQuotesRepository = get()
        )
    }

    viewModel { JournalViewModel(journalRepository = get()) }
    viewModel { FavoriteQuoteViewModel(repository = get()) }
    viewModel { MeditationHistoryViewModel(repository = get()) }
    viewModel { PixabayMusicViewModel(repository = get()) }
    viewModel { NasaPictureViewModel(repository = get()) }
    viewModel { CosmicMeditationViewModel(get()) }
    viewModel { YogaRadioViewModel() }
    viewModel { BuddhistTextViewModel() }
}