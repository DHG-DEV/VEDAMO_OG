package com.example.vedamo.search

/**
 * PRICE COMPARISON — HONEST DATA MODEL
 *
 * Vedamo does not have paid affiliate/API access with Agoda, Booking.com,
 * MakeMyTrip, Uber, etc. Showing real side-by-side prices requires a real
 * commercial relationship with each provider (API keys, contracts, revenue
 * share) — not something achievable by writing code alone. Scraping their
 * sites to fake this would violate their Terms of Service, which this
 * project explicitly avoids.
 *
 * So: every provider defaults to `Unavailable(NOT_AUTHORIZED)` for price and
 * rating. The architecture below (ProviderAdapter interface) is what lets a
 * REAL integration be plugged in later — once Vedamo has actual partner/API
 * access with a given provider — without touching any other file. Until
 * then, the UI must show an honest "not available" state, never a fake number.
 */

sealed class PriceInfo {
    data class Available(val amount: String, val currency: String = "₹") : PriceInfo()
    data class Unavailable(val reason: UnavailableReason) : PriceInfo()
}

sealed class RatingInfo {
    data class Available(val stars: Double, val reviewCount: Int? = null) : RatingInfo()
    data class Unavailable(val reason: UnavailableReason) : RatingInfo()
}

enum class UnavailableReason {
    // No commercial/API partnership exists with this provider yet.
    NOT_AUTHORIZED,
    // A partnership exists but this specific data point isn't returned.
    NOT_SUPPORTED_BY_PROVIDER,
    // Network/API call failed at runtime.
    FETCH_FAILED
}

data class ComparisonCardData(
    val appName: String,
    val logoUrl: String,
    val price: PriceInfo,
    val rating: RatingInfo,
    val etaText: String? = null // e.g. "Delivery in 30-40 min" — only when a real ETA source exists
)

/**
 * Every provider implements this. Adding a real integration for, say,
 * Agoda once you have their affiliate API key means writing ONE new class
 * implementing this interface and registering it in ProviderAdapterRegistry
 * — nothing else in the app needs to change.
 */
interface ProviderAdapter {
    val appName: String
    suspend fun fetchComparisonData(query: String): ComparisonCardData
}

/**
 * The default adapter used for every provider until a real, authorized
 * integration is built for it. Honest by construction — it physically
 * cannot return a fabricated price or rating.
 */
class DefaultProviderAdapter(override val appName: String) : ProviderAdapter {
    override suspend fun fetchComparisonData(query: String): ComparisonCardData {
        val config = AppRegistry.apps[appName]
        return ComparisonCardData(
            appName = appName,
            logoUrl = logoUrlForProvider(appName),
            price = PriceInfo.Unavailable(UnavailableReason.NOT_AUTHORIZED),
            rating = RatingInfo.Unavailable(UnavailableReason.NOT_AUTHORIZED),
            etaText = null
        )
    }
}

fun logoUrlForProvider(appName: String): String {
    val domain = AppRegistry.apps[appName]?.homepageUrl
        ?.removePrefix("https://")
        ?.removePrefix("http://")
        ?.removePrefix("www.")
        ?.substringBefore("/")
        ?: return ""
    return "https://www.google.com/s2/favicons?domain=$domain&sz=128"
}

/**
 * Bridges the ProviderRepository layer (BookingRepository, AgodaRepository,
 * etc. — see ProviderRepository.kt) into the existing ComparisonCardData
 * shape the UI already renders. This is what lets hotel providers route
 * through real repository classes while HomeScreen/ComparisonCard stay
 * completely unchanged.
 */
class RepositoryBackedAdapter(
    override val appName: String,
    private val repository: ProviderRepository
) : ProviderAdapter {
    override suspend fun fetchComparisonData(query: String): ComparisonCardData {
        val context = HotelSearchContext(rawQuery = query)
        return when (val result = repository.fetchOffer(context)) {
            is ProviderOfferResult.Success -> ComparisonCardData(
                appName = appName,
                logoUrl = logoUrlForProvider(appName),
                price = result.offer.price,
                rating = result.offer.rating,
                etaText = result.offer.etaText
            )
            is ProviderOfferResult.Unavailable -> ComparisonCardData(
                appName = appName,
                logoUrl = logoUrlForProvider(appName),
                price = PriceInfo.Unavailable(UnavailableReason.NOT_AUTHORIZED),
                rating = RatingInfo.Unavailable(UnavailableReason.NOT_AUTHORIZED),
                etaText = null
            )
        }
    }
}

object ProviderAdapterRegistry {
    // Hotel providers route through the ProviderRepository layer (real
    // integration point). Every other provider (Zomato, Amazon, etc.) still
    // uses DefaultProviderAdapter since this task was scoped to hotels only.
    private val specialAdapters: Map<String, ProviderAdapter> = listOf(
        "Booking.com", "Agoda", "Goibibo", "MakeMyTrip", "Hotels.com", "Expedia"
    ).mapNotNull { name ->
        ProviderRepositoryRegistry.repositoryFor(name)?.let { repo ->
            name to RepositoryBackedAdapter(name, repo)
        }
    }.toMap()

    fun adapterFor(appName: String): ProviderAdapter =
        specialAdapters[appName] ?: DefaultProviderAdapter(appName)
}