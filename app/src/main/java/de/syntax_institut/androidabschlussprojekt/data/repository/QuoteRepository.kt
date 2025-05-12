package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.domain.model.Quote
import de.syntax_institut.androidabschlussprojekt.domain.remote.client.QuoteApiClient

class QuoteRepository(
    private val client: QuoteApiClient
) {
    private val keywordMap = mapOf(
        "Sleep" to listOf("peace", "rest", "calm", "quiet"),
        "Focus" to listOf("focus", "clarity", "goal", "concentration"),
        "Breathe" to listOf("breath", "air", "inhale", "exhale", "present"),
        "Morning" to listOf("morning", "start", "light", "new", "sun")
    )

    suspend fun getQuoteForCategory(category: String): Quote? {
        val quotes = client.fetchQuotes()
        if (quotes.isEmpty()) return null

        val keywords = keywordMap[category].orEmpty()
        val filtered = quotes.filter { quote ->
            keywords.any { keyword -> quote.text.contains(keyword, ignoreCase = true) }
        }

        return filtered.randomOrNull() ?: quotes.randomOrNull()
    }
    suspend fun getAllQuotes(): List<Quote> {
        return client.fetchQuotes()
    }
}