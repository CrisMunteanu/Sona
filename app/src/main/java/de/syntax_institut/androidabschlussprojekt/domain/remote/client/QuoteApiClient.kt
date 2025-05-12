package de.syntax_institut.androidabschlussprojekt.domain.remote.client

import android.content.Context
import de.syntax_institut.androidabschlussprojekt.domain.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class QuoteApiClient(private val context: Context) {

    private val client = OkHttpClient()

    suspend fun fetchQuotes(): List<Quote> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://type.fit/api/quotes")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    if (!body.isNullOrEmpty()) {
                        return@withContext parseQuotesFromJson(body)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //  Fallback auf lokale quotes.json in assets
        return@withContext loadQuotesFromAssets()
    }

    private fun loadQuotesFromAssets(): List<Quote> {
        return try {
            val inputStream = context.assets.open("quotes.json")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val json = bufferedReader.use { it.readText() }
            parseQuotesFromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun parseQuotesFromJson(json: String): List<Quote> {
        val jsonArray = JSONArray(json)
        return List(jsonArray.length()) { i ->
            val obj = jsonArray.getJSONObject(i)
            Quote(
                text = obj.getString("text"),
                author = obj.optString("author", "Unknown")
            )
        }
    }
}