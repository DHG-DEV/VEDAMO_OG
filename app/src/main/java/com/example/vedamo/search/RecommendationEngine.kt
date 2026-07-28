package com.example.vedamo.search

/**
 * Takes classified intent and returns RANKED apps from the App Registry.
 * Never depends on Gemini to name apps — this guarantees "Train to Banaras"
 * always returns actual train apps, consistently, every time.
 */
object RecommendationEngine {

    // Simple in-memory cache so repeat/similar searches in the same session
    // don't re-call Gemini unnecessarily (Module 7: Performance).
    private val cache = mutableMapOf<String, List<Pair<String, String>>>()

    fun recommend(intentResult: IntentResult, originalQuery: String): List<Pair<String, String>> {
        val cacheKey = "${intentResult.category}|${intentResult.subcategory}"
        cache[cacheKey]?.let { return it }

        val results = mutableListOf<Pair<String, String>>()

        // Low confidence + a genuine alternate reading -> blend both
        // categories' top apps instead of forcing a single (possibly wrong) one.
        if (intentResult.confidence < 0.7 && intentResult.alternateCategory != null) {
            val primary = topAppsFor(intentResult.category, intentResult.subcategory, limit = 3)
            val alternate = topAppsFor(intentResult.alternateCategory, "", limit = 3)
            results.addAll(primary.map { (name, config) ->
                name to "${config.category} match for \"$originalQuery\""
            })
            results.addAll(alternate.map { (name, config) ->
                name to "Could also mean ${intentResult.alternateCategory}: \"$originalQuery\""
            })
        } else {
            val matched = topAppsFor(intentResult.category, intentResult.subcategory, limit = 6)
            results.addAll(matched.map { (name, config) ->
                name to (config.description.ifBlank { "${config.category} option for \"$originalQuery\"" })
            })
        }

        if (results.isEmpty()) {
            results.add("Google Search" to "General web results for \"$originalQuery\"")
        }

        cache[cacheKey] = results
        return results
    }

    private fun topAppsFor(category: String, subcategory: String, limit: Int): List<Pair<String, AppSearchConfig>> {
        val bySubcategory = if (subcategory.isNotBlank()) {
            AppRegistry.appsInSubcategory(category, subcategory)
        } else emptyList()

        return if (bySubcategory.isNotEmpty()) {
            bySubcategory.take(limit)
        } else {
            AppRegistry.appsInCategory(category).take(limit)
        }
    }

    fun clearCache() = cache.clear()
}