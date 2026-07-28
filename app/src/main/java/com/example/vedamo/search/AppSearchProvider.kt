package com.example.vedamo.search

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import java.net.URLEncoder

object AppSearchProvider {

    fun openResult(context: Context, appName: String, query: String): SearchOutcome {
        val config = AppRegistry.apps[appName] ?: return openGenericGoogleSearch(context, query)
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val isInstalled = config.packageName.isNotBlank() &&
                context.packageManager.getLaunchIntentForPackage(config.packageName) != null

        // 1. Genuinely documented custom URI scheme (e.g. Spotify) — highest priority.
        if (query.isNotBlank() && config.uriScheme != null) {
            try {
                val uri = config.uriScheme.replace("{query}", encodedQuery).toUri()
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                return SearchOutcome.OPENED_DEEP_LINK
            } catch (e: Exception) { /* fall through */ }
        }

        // 2. A verified search URL exists — use it. If the app is installed
        // and registered for App Links on that domain, Android hands this
        // off directly to the native app's own search screen (this is the
        // officially-supported mechanism for passing a query to most major
        // apps). If not installed, it opens the correct search results in
        // the browser — genuinely useful even without the app.
        if (query.isNotBlank() && config.webSearchUrl != null) {
            try {
                val uri = "${config.webSearchUrl}$encodedQuery".toUri()
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                return SearchOutcome.OPENED_WEB_SEARCH
            } catch (e: Exception) { /* fall through */ }
        }

        // 3. No query typed, or no search URL exists for this app.
        if (isInstalled) {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(config.packageName)
            try {
                context.startActivity(launchIntent)
                return if (query.isBlank()) SearchOutcome.OPENED_INSTALLED_APP_NO_QUERY
                else SearchOutcome.OPENED_HOMEPAGE_LIMITATION
            } catch (e: Exception) { /* fall through */ }
        }

        // 4. Not installed AND no usable search URL — this is where a
        // Play Store redirect actually adds value (promotes installing the
        // exact right app) instead of showing a homepage with no way to
        // search. Only reached when step 2 couldn't already show real results.
        if (!isInstalled && config.webSearchUrl == null && config.packageName.isNotBlank()) {
            if (openPlayStore(context, config.packageName)) {
                return SearchOutcome.OPENED_PLAY_STORE
            }
        }

        // 5. Last resort: the app's real homepage.
        return try {
            context.startActivity(Intent(Intent.ACTION_VIEW, config.homepageUrl.toUri()))
            SearchOutcome.OPENED_HOMEPAGE_LIMITATION
        } catch (e: Exception) {
            openGenericGoogleSearch(context, query)
        }
    }

    private fun openPlayStore(context: Context, packageName: String): Boolean {
        return try {
            val marketIntent = Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
            context.startActivity(marketIntent)
            true
        } catch (e: Exception) {
            try {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=$packageName".toUri()
                )
                context.startActivity(webIntent)
                true
            } catch (e2: Exception) {
                false
            }
        }
    }

    fun limitationFor(appName: String): String? = AppRegistry.apps[appName]?.limitation

    private fun openGenericGoogleSearch(context: Context, query: String): SearchOutcome {
        return try {
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            context.startActivity(
                Intent(Intent.ACTION_VIEW, "https://www.google.com/search?q=$encodedQuery".toUri())
            )
            SearchOutcome.OPENED_WEB_SEARCH
        } catch (e: Exception) {
            SearchOutcome.FAILED
        }
    }
}