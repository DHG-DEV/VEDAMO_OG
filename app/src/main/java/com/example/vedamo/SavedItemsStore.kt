package com.example.vedamo

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * Real local persistence for "Save" (SharedPreferences-backed — no backend
 * needed for this). Stores which app was saved for which original query, so
 * a future "Saved" screen can show "Zomato — saved from 'burger'" rather
 * than just a bare app name.
 */
object SavedItemsStore {
    private const val PREFS_NAME = "vedamo_saved_items"
    private const val KEY_ITEMS = "items_json"

    data class SavedItem(val appName: String, val query: String, val timestamp: Long)

    fun save(context: Context, appName: String, query: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = getAll(context).toMutableList()

        // Avoid duplicate saves of the exact same app+query pair.
        current.removeAll { it.appName == appName && it.query == query }
        current.add(0, SavedItem(appName, query, System.currentTimeMillis()))

        val jsonArray = JSONArray()
        current.forEach { item ->
            jsonArray.put(JSONObject().apply {
                put("appName", item.appName)
                put("query", item.query)
                put("timestamp", item.timestamp)
            })
        }
        prefs.edit().putString(KEY_ITEMS, jsonArray.toString()).apply()
    }

    fun remove(context: Context, appName: String, query: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = getAll(context).filterNot { it.appName == appName && it.query == query }
        val jsonArray = JSONArray()
        current.forEach { item ->
            jsonArray.put(JSONObject().apply {
                put("appName", item.appName)
                put("query", item.query)
                put("timestamp", item.timestamp)
            })
        }
        prefs.edit().putString(KEY_ITEMS, jsonArray.toString()).apply()
    }

    fun getAll(context: Context): List<SavedItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_ITEMS, null) ?: return emptyList()
        return try {
            val jsonArray = JSONArray(raw)
            (0 until jsonArray.length()).map { i ->
                val obj = jsonArray.getJSONObject(i)
                SavedItem(
                    appName = obj.getString("appName"),
                    query = obj.getString("query"),
                    timestamp = obj.getLong("timestamp")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}