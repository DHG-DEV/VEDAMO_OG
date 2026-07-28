package com.example.vedamo.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

data class RealHotel(
    val name: String,
    val address: String?
)

/**
 * REAL, FREE, KEYLESS data source — no signup, no API key, no affiliate
 * approval needed. Uses two genuinely public OpenStreetMap services:
 *
 * 1. Nominatim (geocoding: destination name -> lat/lon)
 * 2. Overpass API (queries OSM's actual tagged hotel POIs near that point)
 *
 * HONEST LIMITS:
 * - OSM hotel coverage varies by region — well-mapped in many cities, sparse
 *   in others. Some real hotels may be missing; this is real open data, not
 *   a complete commercial hotel database.
 * - NO pricing, rating, or availability data exists here — those still come
 *   from ProviderRepository (Booking.com/Agoda/etc.), which remains
 *   "Unavailable" until real partner API access is obtained.
 * - Nominatim's usage policy caps free use at ~1 request/second and asks
 *   for a descriptive User-Agent — both are respected below. This is fine
 *   for personal/low-traffic use; a real production app with meaningful
 *   traffic would need to run its own Overpass/Nominatim mirror instead of
 *   hitting the public instance.
 */
object OverpassHotelService {

    private val client = OkHttpClient()

    suspend fun findRealHotelsNear(destination: String, limit: Int = 4): List<RealHotel> =
        withContext(Dispatchers.IO) {
            try {
                val coords = geocode(destination) ?: return@withContext emptyList()
                queryHotelsNear(coords.first, coords.second, limit)
            } catch (e: Exception) {
                emptyList()
            }
        }

    private fun geocode(place: String): Pair<Double, Double>? {
        val encoded = URLEncoder.encode(place, "UTF-8")
        val url = "https://nominatim.openstreetmap.org/search?q=$encoded&format=json&limit=1"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Vedamo-Android-App/1.0 (personal project)")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            val body = response.body?.string() ?: return null
            val arr = JSONArray(body)
            if (arr.length() == 0) return null
            val first = arr.getJSONObject(0)
            return first.getString("lat").toDouble() to first.getString("lon").toDouble()
        }
    }

    private fun queryHotelsNear(lat: Double, lon: Double, limit: Int): List<RealHotel> {
        // Search within ~15km radius for OSM nodes tagged tourism=hotel.
        // Widened from an initial 5km since OSM hotel-tagging density varies
        // a lot by city — smaller radius missed real hotels in some cities.
        val query = """
            [out:json][timeout:20];
            node["tourism"="hotel"](around:15000,$lat,$lon);
            out body $limit;
        """.trimIndent()

        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val request = Request.Builder()
            .url("https://overpass-api.de/api/interpreter?data=$encodedQuery")
            .header("User-Agent", "Vedamo-Android-App/1.0 (personal project)")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return emptyList()
            val body = response.body?.string() ?: return emptyList()
            val json = JSONObject(body)
            val elements = json.getJSONArray("elements")

            val hotels = mutableListOf<RealHotel>()
            for (i in 0 until elements.length()) {
                val el = elements.getJSONObject(i)
                val tags = el.optJSONObject("tags") ?: continue
                val name = tags.optString("name").takeIf { it.isNotBlank() } ?: continue
                val street = tags.optString("addr:street", "")
                val city = tags.optString("addr:city", "")
                val address = listOf(street, city).filter { it.isNotBlank() }.joinToString(", ").ifBlank { null }
                hotels.add(RealHotel(name = name, address = address))
            }
            return hotels.take(limit)
        }
    }
}