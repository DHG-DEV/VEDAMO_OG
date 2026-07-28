package com.example.vedamo

import android.content.Context
import com.example.vedamo.search.LinkLauncherService

/**
 * Thin bridge kept for backward compatibility with existing call sites.
 * Real logic lives in LinkLauncherService (install-check, deep link,
 * browser fallback, friendly failure message).
 */
fun openResult(context: Context, appName: String, query: String) {
    LinkLauncherService.open(context, appName, query)
}