package com.example.vedamo.search

import com.example.vedamo.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object IntentEngine {

    private val knownCategories = AppRegistry.apps.values
        .map { it.category }.distinct().sorted()

    suspend fun classifyIntent(query: String): IntentResult? = withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()

            val systemPrompt = """
                You are an intent classifier. Given a user's search query, determine
                ONLY the following — never suggest app names:
                {
                  "intent": "short description of what the user wants",
                  "category": "one of: ${knownCategories.joinToString(", ")}",
                  "subcategory": "a specific subcategory within that category, or empty string",
                  "entities": ["key specific details mentioned, e.g. destination city, price limit, movie title — empty array if none"],
                  "confidence": 0.0 to 1.0,
                  "alternateCategory": "only include this field if the query is genuinely
                     ambiguous between two readings (e.g. 'Apple' = fruit vs company),
                     otherwise omit it"
                }
                Return ONLY this JSON, no markdown, no other text.
            """.trimIndent()

            val requestBodyJson = JSONObject().apply {
                put("contents", JSONArray().put(
                    JSONObject().put("parts", JSONArray().put(
                        JSONObject().put("text", "$systemPrompt\n\nUser query: $query")
                    ))
                ))
            }

            val body = requestBodyJson.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${BuildConfig.GEMINI_API_KEY}")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val responseBody = response.body?.string() ?: return@withContext null

                val json = JSONObject(responseBody)
                val text = json.getJSONArray("candidates").getJSONObject(0)
                    .getJSONObject("content").getJSONArray("parts").getJSONObject(0)
                    .getString("text")

                val cleaned = text.replace("```json", "").replace("```", "").trim()
                val result = JSONObject(cleaned)

                val entitiesArray = result.optJSONArray("entities")
                val entities = mutableListOf<String>()
                if (entitiesArray != null) {
                    for (i in 0 until entitiesArray.length()) {
                        entities.add(entitiesArray.getString(i))
                    }
                }

                IntentResult(
                    intent = result.optString("intent", query),
                    category = result.optString("category", "General"),
                    subcategory = result.optString("subcategory", ""),
                    entities = entities,
                    confidence = result.optDouble("confidence", 0.5),
                    alternateCategory = if (result.has("alternateCategory")) result.getString("alternateCategory") else null
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}