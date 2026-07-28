package com.example.vedamo.search

data class AppSearchConfig(
    val packageName: String,
    val uriScheme: String? = null,
    val webSearchUrl: String? = null,
    val homepageUrl: String,
    val limitation: String? = null,
    val category: String = "General",
    val subcategory: String = "",
    val keywords: List<String> = emptyList(),
    val priority: Int = 50,
    val description: String = ""
)

data class IntentResult(
    val intent: String,
    val category: String,
    val subcategory: String,
    // Extracted specifics from the query, e.g. "Train to Banaras" -> ["Banaras"],
    // "Laptop under ₹70000" -> ["70000", "budget"]. Not currently used to
    // filter apps (the Registry ranks by category/subcategory), but stored
    // so a future step (e.g. passing a destination city, a price filter)
    // can use it without re-classifying the query.
    val entities: List<String> = emptyList(),
    val confidence: Double,
    val alternateCategory: String? = null
)

enum class SearchOutcome {
    OPENED_DEEP_LINK,
    OPENED_WEB_SEARCH,
    OPENED_INSTALLED_APP_NO_QUERY,
    OPENED_PLAY_STORE,
    OPENED_HOMEPAGE_LIMITATION,
    FAILED
}