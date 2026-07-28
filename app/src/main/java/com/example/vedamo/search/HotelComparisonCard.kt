package com.example.vedamo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vedamo.search.ComparisonCardData
import com.example.vedamo.search.PriceInfo
import com.example.vedamo.search.RatingInfo

private val TableHeaderNavy = Color(0xFF16213E)
private val AvailableGreen = Color(0xFF1B9E4B)
private val MutedGray = Color(0xFF6B7280)

/**
 * Hotel-category-specific styling (dark navy table header, provider-branded
 * name colors, honest fallback states) applied to the SAME ComparisonCardData
 * already produced by ComparisonEngine/ProviderAdapter — no new/fake data
 * source. Used only when the search category is Hotels; every other
 * category keeps the existing generic ComparisonCard grid untouched.
 *
 * No specific hotel name/photo is shown (e.g. "Taj Mahal Palace") because
 * that requires a real per-hotel search API from each provider, which
 * Vedamo doesn't have authorized access to yet — see ProviderAdapter.kt.
 */
@Composable
fun HotelComparisonSection(
    query: String,
    providers: List<ComparisonCardData>,
    onOpenApp: (ComparisonCardData) -> Unit,
    onSave: (ComparisonCardData) -> Unit,
    onShare: (ComparisonCardData) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Hotel options for \"$query\"",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A2744)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Comparing ${providers.size} booking apps",
                fontSize = 12.sp,
                color = MutedGray
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Table header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TableHeaderNavy, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .padding(vertical = 10.dp, horizontal = 12.dp)
            ) {
                Text("Booking App", modifier = Modifier.weight(1.1f), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Text("Price", modifier = Modifier.weight(0.8f), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Text("Rating", modifier = Modifier.weight(0.9f), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(72.dp)) // space for action icons column
            }

            providers.forEachIndexed { index, data ->
                HotelProviderRow(
                    data = data,
                    isLast = index == providers.lastIndex,
                    onOpenApp = { onOpenApp(data) },
                    onSave = { onSave(data) },
                    onShare = { onShare(data) }
                )
            }
        }
    }
}

@Composable
private fun HotelProviderRow(
    data: ComparisonCardData,
    isLast: Boolean,
    onOpenApp: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    val brandColor = providerBrandColor(data.appName)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                data.appName,
                modifier = Modifier.weight(1.1f),
                color = brandColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )

            Box(modifier = Modifier.weight(0.8f)) {
                when (val price = data.price) {
                    is PriceInfo.Available -> Text(
                        "${price.currency}${price.amount}",
                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A2744)
                    )
                    is PriceInfo.Unavailable -> Text("Check on app", fontSize = 11.sp, color = MutedGray)
                }
            }

            Box(modifier = Modifier.weight(0.9f)) {
                when (val rating = data.rating) {
                    is RatingInfo.Available -> Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("${rating.stars}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A2744))
                    }
                    is RatingInfo.Unavailable -> Text("Not available", fontSize = 11.sp, color = MutedGray)
                }
            }

            Row(modifier = Modifier.width(72.dp)) {
                IconButton(onClick = onOpenApp, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = "Open", tint = brandColor, modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = onSave, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = "Save", tint = MutedGray, modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = onShare, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = MutedGray, modifier = Modifier.size(16.dp))
                }
            }
        }
        if (!isLast) {
            HorizontalDivider(color = Color(0xFFEDEFF3), thickness = 1.dp)
        }
    }
}

private fun providerBrandColor(appName: String): Color = when (appName) {
    "Booking.com" -> Color(0xFF003580)
    "OYO" -> Color(0xFFEF4B4B)
    "Agoda" -> Color(0xFF1A2744)
    "MakeMyTrip" -> Color(0xFFEF6C00)
    "Airbnb" -> Color(0xFFFF5A5F)
    "Goibibo" -> Color(0xFFFF7300)
    else -> Color(0xFF1A2744)
}