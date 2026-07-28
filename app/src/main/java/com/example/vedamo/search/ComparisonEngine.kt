package com.example.vedamo.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * Takes a list of app names (already ranked by RecommendationEngine) and
 * fetches comparison data for each from its adapter. Runs them concurrently
 * since each is an independent (currently instant, future: network) call.
 */
object ComparisonEngine {

    suspend fun buildComparison(appNames: List<String>, query: String): List<ComparisonCardData> =
        withContext(Dispatchers.IO) {
            appNames.map { appName ->
                async {
                    ProviderAdapterRegistry.adapterFor(appName).fetchComparisonData(query)
                }
            }.awaitAll()
        }
}