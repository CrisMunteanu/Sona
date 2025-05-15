package de.syntax_institut.androidabschlussprojekt.domain.remote.client

import  android.content.Context
import de.syntax_institut.androidabschlussprojekt.domain.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class QuoteApiClientRealInspire(private val context: Context) {

    suspend fun fetchRandomQuote(): Quote? = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://api.realinspire.live/v1/quotes/random")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == 200) {
                val json = connection.inputStream.bufferedReader().readText()
                val obj = JSONObject(json)
                val quote = obj.getString("quote")
                val author = obj.optString("author", "Unknown")
                return@withContext Quote(quote, author)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}