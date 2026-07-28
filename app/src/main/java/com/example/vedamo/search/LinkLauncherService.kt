package com.example.vedamo.search

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import java.net.URLEncoder

enum class LaunchResult {
    OPENED_INSTALLED_APP,
    OPENED_DEEP_LINK,
    OPENED_BROWSER,
    FAILED
}

/**
 * Reusable launcher used by every provider's "Open App" button. Centralizes
 * the install-check → deep link → browser → friendly-failure logic in one
 * place, so no provider-specific branching lives in the UI.
 *
 * Adding a new provider: add one entry to AppRegistry.apps — nothing here
 * needs to change.
 */
object LinkLauncherService {

    fun open(context: Context, appName: String, query: String): LaunchResult {
        val config = AppRegistry.apps[appName]
        if (config == null) {
            return openGenericSearch(context, query)
        }

        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val isInstalled = config.packageName.isNotBlank() &&
                context.packageManager.getLaunchIntentForPackage(config.packageName) != null

        // 1. Documented custom URI scheme (e.g. Spotify) — most direct.
        if (query.isNotBlank() && config.uriScheme != null) {
            try {
                val uri = config.uriScheme.replace("{query}", encodedQuery).toUri()
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                return LaunchResult.OPENED_DEEP_LINK
            } catch (e: Exception) { /* fall through */ }
        }

        // 2. Verified search URL — if the app is installed and registered
        // for App Links on that domain, Android hands this to the native
        // app directly; otherwise it opens correct results in browser.
        if (query.isNotBlank() && config.webSearchUrl != null) {
            try {
                val uri = "${config.webSearchUrl}$encodedQuery".toUri()
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                return if (isInstalled) LaunchResult.OPENED_INSTALLED_APP else LaunchResult.OPENED_BROWSER
            } catch (e: Exception) { /* fall through */ }
        }

        // 3. No query, or no search URL — open the installed app directly.
        if (isInstalled) {
            try {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(config.packageName)
                context.startActivity(launchIntent)
                return LaunchResult.OPENED_INSTALLED_APP
            } catch (e: Exception) { /* fall through */ }
        }

        // 4. Not installed, no search URL — try the homepage in browser.
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, config.homepageUrl.toUri()))
            return LaunchResult.OPENED_BROWSER
        } catch (e: Exception) { /* fall through */ }

        // 5. Nothing worked — tell the user honestly instead of failing silently.
        Toast.makeText(
            context,
            "Couldn't open $appName — it may not be installed and no browser is available.",
            Toast.LENGTH_LONG
        ).show()
        return LaunchResult.FAILED
    }

    private fun openGenericSearch(context: Context, query: String): LaunchResult {
        return try {
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            context.startActivity(
                Intent(Intent.ACTION_VIEW, "https://www.google.com/search?q=$encodedQuery".toUri())
            )
            LaunchResult.OPENED_BROWSER
        } catch (e: Exception) {
            Toast.makeText(context, "Couldn't open a browser to search.", Toast.LENGTH_LONG).show()
            LaunchResult.FAILED
        }
    }
}