package com.example.vedamo.search

/**
 * Goibibo — real integration requires an approved affiliate/partner
 * account with Goibibo (no public partner API currently documented), which Vedamo does not
 * currently have. Returns Unavailable honestly rather than a fabricated price.
 *
 * TO ACTIVATE ONCE CREDENTIALS EXIST:
 * 1. Add the API key to local.properties + BuildConfig (same pattern as
 *    GEMINI_API_KEY in build.gradle.kts).
 * 2. Replace the body of fetchOffer() with a real OkHttp/Retrofit call to
 *    Goibibo's documented search endpoint.
 * 3. Map their JSON response fields into ProviderOffer — nothing else in
 *    the app needs to change, since ComparisonEngine/UI only ever see
 *    ProviderOfferResult.
 */
class GoibiboRepository : ProviderRepository {
    override val providerName: String = "Goibibo"

    override suspend fun fetchOffer(context: HotelSearchContext): ProviderOfferResult {
        // No API key configured — this is the true current state, not a bug.
        return ProviderOfferResult.Unavailable(
            "No authorized Goibibo API access configured yet"
        )
    }
}