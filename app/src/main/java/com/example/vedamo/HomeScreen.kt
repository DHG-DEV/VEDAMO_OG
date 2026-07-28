package com.example.vedamo

import android.content.Intent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vedamo.ui.theme.BackgroundLight
import com.example.vedamo.ui.theme.LocalAppGradient
import com.example.vedamo.search.AppRegistry
import com.example.vedamo.search.ComparisonCardData
import com.example.vedamo.search.ComparisonEngine
import com.example.vedamo.search.IntentEngine
import com.example.vedamo.search.PriceInfo
import com.example.vedamo.search.RatingInfo
import com.example.vedamo.search.RecommendationEngine
import kotlinx.coroutines.launch

data class BrowseCategory(
    val title: String,
    val appNames: List<String>
)

val browseCategories = listOf(
    BrowseCategory("Learning", listOf("Coursera", "Udemy", "edX", "freeCodeCamp", "Khan Academy", "LeetCode", "HackerRank", "YouTube")),
    BrowseCategory("Shopping", listOf("Amazon", "Flipkart", "Myntra", "Ajio", "Nykaa", "Croma")),
    BrowseCategory("Travel", listOf("IRCTC", "ConfirmTkt", "RailYatri", "ixigo", "Skyscanner", "Cleartrip")),
    BrowseCategory("Hotels & Stays", listOf("Booking.com", "Agoda", "OYO", "Airbnb", "MakeMyTrip")),
    BrowseCategory("Food Delivery", listOf("Zomato", "Swiggy", "Blinkit", "Zepto", "BigBasket")),
    BrowseCategory("Fitness", listOf("Strong", "Hevy", "MyFitnessPal")),
    BrowseCategory("Finance", listOf("Paytm", "PhonePe", "Google Pay", "Zerodha", "Groww")),
    BrowseCategory("Jobs", listOf("LinkedIn", "Naukri", "Indeed"))
)

@Composable
fun AppLogo(logoUrl: String, fallbackLetter: String, size: androidx.compose.ui.unit.Dp) {
    var loadFailed by remember(logoUrl) { mutableStateOf(false) }
    Box(
        modifier = Modifier.size(size).clip(CircleShape).background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (logoUrl.isNotBlank() && !loadFailed) {
            AsyncImage(
                model = logoUrl,
                contentDescription = fallbackLetter,
                contentScale = ContentScale.Fit,
                onError = { loadFailed = true },
                modifier = Modifier.size(size * 0.85f).clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(fallbackLetter.take(1), fontWeight = FontWeight.Bold, fontSize = (size.value / 2.2).sp, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun SearchingIndicator(gradientStart: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "searching")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f, targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(600, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "scale"
    )
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(56.dp).scale(scale).background(gradientStart, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Finding the best matches...", fontSize = 14.sp, color = Color.Gray)
    }
}

/**
 * One provider's comparison card. Price/rating show an honest "Not
 * available" state instead of a fake number whenever we lack authorized
 * data for that provider — see ProviderAdapter.kt for why.
 */
@Composable
fun ComparisonCard(
    data: ComparisonCardData,
    gradientStart: Color,
    onOpenApp: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppLogo(data.logoUrl, data.appName, 44.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(data.appName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = gradientStart)
                    Spacer(modifier = Modifier.height(2.dp))
                    when (val rating = data.rating) {
                        is RatingInfo.Available -> Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${rating.stars}", fontSize = 12.sp, color = Color.DarkGray)
                        }
                        is RatingInfo.Unavailable -> Text("Rating not available", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                when (val price = data.price) {
                    is PriceInfo.Available -> Text(
                        "${price.currency}${price.amount}",
                        fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF16A34A)
                    )
                    is PriceInfo.Unavailable -> Text(
                        "Check on app", fontSize = 12.sp, color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.End
                    )
                }
            }

            if (data.etaText != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(data.etaText, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onOpenApp,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = gradientStart),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Open App", fontSize = 13.sp)
                }
                IconButton(onClick = onSave) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = "Save")
                }
                IconButton(onClick = onShare) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<ComparisonCardData>>(emptyList()) }
    var resultCategory by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var hasSearched by remember { mutableStateOf(false) }
    // Completely separate from the real search state above — this only
    // toggles a standalone screen with fictional demo data (DesignPreviewScreen.kt).
    var showDesignPreview by remember { mutableStateOf(false) }
    // Real hotel names from OpenStreetMap (free, keyless) — only populated
    // for hotel-category searches. Empty means either not a hotel search,
    // or OSM had no coverage for that destination.
    var realHotels by remember { mutableStateOf<List<com.example.vedamo.search.RealHotel>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val gradient = LocalAppGradient.current

    if (showDesignPreview) {
        DesignPreviewScreen(onBack = { showDesignPreview = false })
        return
    }

    fun shareProvider(data: ComparisonCardData) {
        val config = AppRegistry.apps[data.appName]
        val shareText = "Check out ${data.appName} for \"$query\": ${config?.homepageUrl ?: ""}"
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(sendIntent, "Share via"))
    }

    fun saveProvider(data: ComparisonCardData) {
        SavedItemsStore.save(context, data.appName, query)
    }

    Column(modifier = Modifier.fillMaxSize().background(BackgroundLight)) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(gradient.start, gradient.end)))
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            Column {
                Text("Vedamo", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("What are you looking for?", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search anything...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    isLoading = true
                    hasSearched = true
                    coroutineScope.launch {
                        val intentResult = IntentEngine.classifyIntent(query)
                        val rankedNames = if (intentResult != null) {
                            resultCategory = intentResult.category
                            RecommendationEngine.recommend(intentResult, query).map { it.first }
                        } else {
                            val matched = AppRegistry.apps.entries
                                .filter { (_, c) -> c.keywords.any { it in query.lowercase() } }
                                .sortedByDescending { it.value.priority }
                            resultCategory = matched.firstOrNull()?.value?.category
                            matched.map { it.key }.ifEmpty { listOf("Google Search") }
                        }
                        results = ComparisonEngine.buildComparison(rankedNames, query)

                        val isHotelSearch = resultCategory == "Travel" &&
                                results.any { AppRegistry.apps[it.appName]?.subcategory == "Hotel" }

                        realHotels = if (isHotelSearch) {
                            // Extract a destination guess from the query for geocoding —
                            // e.g. "hotels in Kanpur" -> "Kanpur". Simple heuristic, not
                            // a full NLP parse; falls back to the whole query if no
                            // "in"/"at"/"near" marker is found.
                            val destination = Regex("(?:in|at|near)\\s+(.+)", RegexOption.IGNORE_CASE)
                                .find(query)?.groupValues?.get(1)?.trim()
                                ?: query
                            com.example.vedamo.search.OverpassHotelService.findRealHotelsNear(destination)
                        } else {
                            emptyList()
                        }

                        isLoading = false
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = gradient.start),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Search", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (isLoading) {
            SearchingIndicator(gradientStart = gradient.start)
        } else if (!hasSearched) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(browseCategories) { category ->
                    Column {
                        Text(category.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            category.appNames.forEach { name ->
                                val logoUrl = com.example.vedamo.search.logoUrlForProvider(name)
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.width(64.dp).clickable { openResult(context, name, "") }
                                ) {
                                    AppLogo(logoUrl, name, 52.dp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(name, fontSize = 11.sp, color = Color.DarkGray, maxLines = 1)
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { showDesignPreview = true }) {
                        Text(
                            "View design concept preview (sample data, not live)",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                val isHotelCategory = resultCategory == "Travel" &&
                        results.any { AppRegistry.apps[it.appName]?.subcategory == "Hotel" }

                if (isHotelCategory) {
                    if (realHotels.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            realHotels.forEach { hotel ->
                                Column {
                                    Text(hotel.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2744))
                                    if (hotel.address != null) {
                                        Text(hotel.address, fontSize = 11.sp, color = Color.Gray)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    HotelComparisonSection(
                                        query = "${hotel.name} ${query}",
                                        providers = results,
                                        onOpenApp = { data -> openResult(context, data.appName, "${hotel.name} $query") },
                                        onSave = { data -> saveProvider(data) },
                                        onShare = { data -> shareProvider(data) }
                                    )
                                }
                            }
                            Text(
                                "Hotel names sourced from OpenStreetMap — real, but not exhaustive. Prices/ratings above are not available without provider partner access.",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        // Honest: we tried OSM, either the destination wasn't
                        // found or has no mapped hotels nearby.
                        HotelComparisonSection(
                            query = query,
                            providers = results,
                            onOpenApp = { data -> openResult(context, data.appName, query) },
                            onSave = { data -> saveProvider(data) },
                            onShare = { data -> shareProvider(data) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Couldn't find specific hotel names for this destination — showing booking apps to search directly instead.",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                } else {
                    Text(
                        "Comparing ${results.size} option${if (results.size != 1) "s" else ""}",
                        fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(results) { data ->
                            ComparisonCard(
                                data = data,
                                gradientStart = gradient.start,
                                onOpenApp = { openResult(context, data.appName, query) },
                                onSave = { saveProvider(data) },
                                onShare = { shareProvider(data) }
                            )
                        }
                    }
                }
            }
        }
    }
}