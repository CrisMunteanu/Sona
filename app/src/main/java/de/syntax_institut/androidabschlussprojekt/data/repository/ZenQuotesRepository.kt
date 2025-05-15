package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.domain.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class ZenQuotesRepository {

    suspend fun getRandomQuote(): Quote? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://zenquotes.io/api/random")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val data = connection.inputStream.bufferedReader().readText()
                val json = JSONArray(data).getJSONObject(0)

                val text = json.getString("q")
                val author = json.getString("a")

                Quote(text, author)
            } catch (e: Exception) {
                e.printStackTrace()
                Quote("Stay calm and breathe.", "Sona")
            }
        }
    }
}