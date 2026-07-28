package com.example.vedamo.search

/**
 * Booking.com — real integration requires an approved affiliate/partner
 * account with Booking.com (Booking.com Demand API / Affiliate Partner Program), which Vedamo does not
 * currently have. Returns Unavailable honestly rather than a fabricated price.
 *
 * TO ACTIVATE ONCE CREDENTIALS EXIST:
 * 1. Add the API key to local.properties + BuildConfig (same pattern as
 *    GEMINI_API_KEY in build.gradle.kts).
 * 2. Replace the body of fetchOffer() with a real OkHttp/Retrofit call to
 *    Booking.com's documented search endpoint.
 * 3. Map their JSON response fields into ProviderOffer — nothing else in
 *    the app needs to change, since ComparisonEngine/UI only ever see
 *    ProviderOfferResult.
 */
class BookingRepository : ProviderRepository {
    override val providerName: String = "Booking.com"

    override suspend fun fetchOffer(context: HotelSearchContext): ProviderOfferResult {
        // No API key configured — this is the true current state, not a bug.
        return ProviderOfferResult.Unavailable(
            "No authorized Booking.com API access configured yet"
        )
    }
}