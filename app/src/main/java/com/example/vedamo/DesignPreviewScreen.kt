package com.example.vedamo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ⚠️ DESIGN PREVIEW ONLY — NOT REAL DATA ⚠️
 *
 * Every hotel name, price, rating, and availability number on this screen
 * is INVENTED for visual demonstration purposes only. This screen is NOT
 * reachable from the real search flow (see HomeScreen.kt) and must never
 * be wired to real search results. It exists purely so the visual design
 * from the reference mockup can be shown to someone without presenting
 * fabricated prices as real data to actual users.
 *
 * If real Booking.com/Agoda/OYO/MakeMyTrip partner API access is obtained
 * in the future, replace this entire screen's data source with
 * ProviderRepositoryRegistry — do not simply "promote" this fake data into
 * the live app.
 */

private data class FakeProviderRow(
    val name: String,
    val brandColor: Color,
    val price: String,
    val roomsLeft: Int,
    val rating: Double,
    val reviewCount: Int
)

private data class FakeHotelCard(
    val name: String,
    val location: String,
    val overallRating: Double,
    val facilities: List<String>,
    val providers: List<FakeProviderRow>
)

private val sampleHotels = listOf(
    FakeHotelCard(
        name = "Taj Mahal Palace, Mumbai",
        location = "Apollo Bunder, Mumbai",
        overallRating = 4.8,
        facilities = listOf("Free Wi-Fi", "Pool", "Spa", "Gym", "Restaurant", "Bar", "Breakfast", "Parking"),
        providers = listOf(
            FakeProviderRow("Booking.com", Color(0xFF003580), "₹12,999", 5, 4.6, 3245),
            FakeProviderRow("OYO", Color(0xFFEF4B4B), "₹11,999", 3, 4.3, 1982),
            FakeProviderRow("Agoda", Color(0xFF16213E), "₹12,450", 4, 4.5, 2889),
            FakeProviderRow("MakeMyTrip", Color(0xFFEF6C00), "₹12,799", 6, 4.6, 3112)
        )
    ),
    FakeHotelCard(
        name = "Taj Lake Palace, Udaipur",
        location = "Pichola Lake, Udaipur",
        overallRating = 4.9,
        facilities = listOf("Lake View", "Free Wi-Fi", "Pool", "Spa", "Restaurant", "Bar", "Boating", "Butler"),
        providers = listOf(
            FakeProviderRow("Booking.com", Color(0xFF003580), "₹18,499", 3, 4.8, 2104),
            FakeProviderRow("OYO", Color(0xFFEF4B4B), "₹17,999", 2, 4.7, 1256),
            FakeProviderRow("Agoda", Color(0xFF16213E), "₹18,199", 3, 4.8, 1876),
            FakeProviderRow("MakeMyTrip", Color(0xFFEF6C00), "₹18,699", 4, 4.9, 2034)
        )
    )
)

@Composable
fun DesignPreviewScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F6F8))) {

        // Persistent, impossible-to-miss warning banner.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF3CD))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFF8A6D00), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "SAMPLE DESIGN PREVIEW — All hotel names, prices, and ratings below are invented for demonstration. Not real data.",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF8A6D00)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Design Preview", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleHotels) { hotel ->
                FakeHotelCardView(hotel)
            }
        }
    }
}

@Composable
private fun FakeHotelCardView(hotel: FakeHotelCard) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(hotel.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2744))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text("${hotel.overallRating}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2744))
                Spacer(modifier = Modifier.width(10.dp))
                Text(hotel.location, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                hotel.facilities.joinToString(" · "),
                fontSize = 11.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF16213E), RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                Text("Booking App", modifier = Modifier.weight(1.1f), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                Text("Price", modifier = Modifier.weight(0.8f), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                Text("Available", modifier = Modifier.weight(0.9f), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                Text("Rating", modifier = Modifier.weight(0.9f), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }

            val cheapest = hotel.providers.minByOrNull {
                it.price.replace("₹", "").replace(",", "").toIntOrNull() ?: Int.MAX_VALUE
            }

            hotel.providers.forEachIndexed { index, p ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(p.name, modifier = Modifier.weight(1.1f), color = p.brandColor, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Row(modifier = Modifier.weight(0.8f), verticalAlignment = Alignment.CenterVertically) {
                        Text(p.price, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2744))
                        if (p == cheapest) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier.background(Color(0xFF1B9E4B), RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text("BEST", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Column(modifier = Modifier.weight(0.9f)) {
                        Text("Available", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B9E4B))
                        Text("${p.roomsLeft} rooms left", fontSize = 9.sp, color = Color.Gray)
                    }
                    Column(modifier = Modifier.weight(0.9f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${p.rating}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A2744))
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(11.dp))
                        }
                        Text("(${p.reviewCount})", fontSize = 9.sp, color = Color.Gray)
                    }
                }
                if (index != hotel.providers.lastIndex) {
                    HorizontalDivider(color = Color(0xFFEDEFF3), thickness = 1.dp)
                }
            }
        }
    }
}
