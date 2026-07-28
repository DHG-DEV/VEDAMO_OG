package com.example.vedamo.search

/**
 * Normalized models the UI actually consumes. Repositories below produce
 * ProviderOfferResult; the UI never sees provider-specific response shapes.
 */

data class ProviderOffer(
    val providerName: String,
    val price: PriceInfo,
    val rating: RatingInfo,
    val etaText: String? = null
)

sealed class ProviderOfferResult {
    data class Success(val offer: ProviderOffer) : ProviderOfferResult()
    data class Unavailable(val reason: String) : ProviderOfferResult()
}

/**
 * Represents the search context (destination/dates/etc as parsed from the
 * user's query). NOT a real hotel listing — Vedamo has no per-hotel search
 * API access from any provider, so there is no real hotel name/address/photo
 * to populate here. This exists so the architecture is ready to hold that
 * data the moment a real hotel-search integration exists.
 */
data class HotelSearchContext(
    val rawQuery: String,
    val destination: String? = null
)