package com.example.vedamo.search

/**
 * Common interface every provider repository implements. Adding a new
 * provider = one new class implementing this interface + one registration
 * line in ProviderRepositoryRegistry. Nothing else changes.
 */
interface ProviderRepository {
    val providerName: String
    suspend fun fetchOffer(context: HotelSearchContext): ProviderOfferResult
}

object ProviderRepositoryRegistry {
    private val repositories: Map<String, ProviderRepository> = mapOf(
        "Booking.com" to BookingRepository(),
        "Agoda" to AgodaRepository(),
        "Goibibo" to GoibiboRepository(),
        "MakeMyTrip" to MakeMyTripRepository(),
        "Hotels.com" to HotelsRepository(),
        "Expedia" to ExpediaRepository()
    )

    fun repositoryFor(providerName: String): ProviderRepository? = repositories[providerName]
}