package com.example.vedamo.search

/**
 * Agoda — real integration requires an approved affiliate/partner
 * account with Agoda (Agoda Affiliate Program / Partner API), which Vedamo does not
 * currently have. Returns Unavailable honestly rather than a fabricated price.
 *
 * TO ACTIVATE ONCE CREDENTIALS EXIST:
 * 1. Add the API key to local.properties + BuildConfig (same pattern as
 *    GEMINI_API_KEY in build.gradle.kts).
 * 2. Replace the body of fetchOffer() with a real OkHttp/Retrofit call to
 *    Agoda's documented search endpoint.
 * 3. Map their JSON response fields into ProviderOffer — nothing else in
 *    the app needs to change, since ComparisonEngine/UI only ever see
 *    ProviderOfferResult.
 */
class AgodaRepository : ProviderRepository {
    override val providerName: String = "Agoda"

    override suspend fun fetchOffer(context: HotelSearchContext): ProviderOfferResult {
        // No API key configured — this is the true current state, not a bug.
        return ProviderOfferResult.Unavailable(
            "No authorized Agoda API access configured yet"
        )
    }
}