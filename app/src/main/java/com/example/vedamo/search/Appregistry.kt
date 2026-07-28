package com.example.vedamo.search

/**
 * Adding a new app only requires one new entry here. Category/subcategory/
 * keywords/priority drive the Recommendation Engine — the app names Vedamo
 * suggests come from THIS registry, not from Gemini guessing freely.
 */
object AppRegistry {

    val apps: Map<String, AppSearchConfig> = mapOf(

        // ===================== TRAVEL: TRAIN =====================
        "IRCTC" to AppSearchConfig(
            packageName = "cris.org.in.prs.ima",
            homepageUrl = "https://www.irctc.co.in/",
            category = "Travel", subcategory = "Train",
            keywords = listOf("train", "railway", "irctc", "pnr", "tatkal"),
            priority = 90,
            description = "Official Indian Railways booking"
        ),
        "ConfirmTkt" to AppSearchConfig(
            packageName = "com.confirmtkt.lite",
            homepageUrl = "https://www.confirmtkt.com/",
            category = "Travel", subcategory = "Train",
            keywords = listOf("train", "railway", "pnr status", "seat availability"),
            priority = 75,
            description = "Train booking with PNR/seat prediction"
        ),
        "RailYatri" to AppSearchConfig(
            packageName = "com.railyatri.in.mobile",
            homepageUrl = "https://www.railyatri.in/",
            category = "Travel", subcategory = "Train",
            keywords = listOf("train", "railway", "live train status"),
            priority = 70,
            description = "Train booking and live tracking"
        ),
        "ixigo" to AppSearchConfig(
            packageName = "com.ixigo.train.ixitrain",
            homepageUrl = "https://www.ixigo.com/",
            category = "Travel", subcategory = "Train",
            keywords = listOf("train", "flight", "bus", "travel booking"),
            priority = 70,
            description = "Train, flight and bus booking"
        ),

        // ===================== TRAVEL: FLIGHT =====================
        "Skyscanner" to AppSearchConfig(
            packageName = "net.skyscanner.android.main",
            homepageUrl = "https://www.skyscanner.co.in/",
            category = "Travel", subcategory = "Flight",
            keywords = listOf("flight", "fly", "airfare", "airline"),
            priority = 80,
            description = "Flight comparison"
        ),
        "Google Flights" to AppSearchConfig(
            packageName = "",
            webSearchUrl = "https://www.google.com/travel/flights?q=",
            homepageUrl = "https://www.google.com/travel/flights",
            category = "Travel", subcategory = "Flight",
            keywords = listOf("flight", "fly", "airfare"),
            priority = 85,
            description = "Flight search by Google"
        ),
        "Cleartrip" to AppSearchConfig(
            packageName = "com.cleartrip.android",
            homepageUrl = "https://www.cleartrip.com/",
            category = "Travel", subcategory = "Flight",
            keywords = listOf("flight", "hotel", "train booking India"),
            priority = 70,
            description = "Flight, hotel and train booking"
        ),

        // ===================== TRAVEL: HOTEL =====================
        "Booking.com" to AppSearchConfig(
            packageName = "com.booking",
            webSearchUrl = "https://www.booking.com/searchresults.html?ss=",
            homepageUrl = "https://www.booking.com/",
            category = "Travel", subcategory = "Hotel",
            keywords = listOf("hotel", "stay", "room", "accommodation"),
            priority = 85,
            description = "Global hotel booking"
        ),
        "Agoda" to AppSearchConfig(
            packageName = "com.agoda.mobile.consumer",
            homepageUrl = "https://www.agoda.com/",
            limitation = "Agoda's search requires city/date IDs, not a plain query. Opens homepage.",
            category = "Travel", subcategory = "Hotel",
            keywords = listOf("hotel", "stay"),
            priority = 75,
            description = "Hotel booking, strong in Asia"
        ),
        "OYO" to AppSearchConfig(
            packageName = "com.oyo.consumer",
            homepageUrl = "https://www.oyorooms.com/",
            category = "Travel", subcategory = "Hotel",
            keywords = listOf("hotel", "budget stay"),
            priority = 75,
            description = "Budget hotel chain, India"
        ),
        "Airbnb" to AppSearchConfig(
            packageName = "com.airbnb.android",
            webSearchUrl = "https://www.airbnb.co.in/s/",
            homepageUrl = "https://www.airbnb.co.in/",
            category = "Travel", subcategory = "Hotel",
            keywords = listOf("hotel", "homestay", "vacation rental"),
            priority = 80,
            description = "Homestays and alternative lodging"
        ),
        "MakeMyTrip" to AppSearchConfig(
            packageName = "com.makemytrip",
            homepageUrl = "https://www.makemytrip.com/",
            limitation = "Requires city/date IDs, not a plain query. Opens homepage.",
            category = "Travel", subcategory = "Hotel",
            keywords = listOf("hotel", "flight", "holiday package"),
            priority = 80,
            description = "India's largest travel booking platform"
        ),

        // ===================== TRAVEL: TAXI =====================
        "Uber" to AppSearchConfig(
            packageName = "com.ubercab",
            homepageUrl = "https://www.uber.com/",
            limitation = "Deep links are for requesting rides at coordinates, not keyword search.",
            category = "Travel", subcategory = "Taxi",
            keywords = listOf("taxi", "cab", "ride"),
            priority = 85,
            description = "Ride-hailing"
        ),
        "Ola" to AppSearchConfig(
            packageName = "com.olacabs.customer",
            homepageUrl = "https://www.olacabs.com/",
            limitation = "No public keyword-search deep link.",
            category = "Travel", subcategory = "Taxi",
            keywords = listOf("taxi", "cab", "auto"),
            priority = 80,
            description = "Ride-hailing, India"
        ),

        // ===================== FOOD =====================
        "Zomato" to AppSearchConfig(
            packageName = "com.application.zomato",
            webSearchUrl = "https://www.zomato.com/search?q=",
            homepageUrl = "https://www.zomato.com/",
            category = "Food", subcategory = "Delivery",
            keywords = listOf("food", "burger", "pizza", "restaurant", "eat", "hungry"),
            priority = 90,
            description = "Food delivery and restaurant discovery"
        ),
        "Swiggy" to AppSearchConfig(
            packageName = "in.swiggy.android",
            webSearchUrl = "https://www.swiggy.com/search?query=",
            homepageUrl = "https://www.swiggy.com/",
            category = "Food", subcategory = "Delivery",
            keywords = listOf("food", "burger", "pizza", "eat", "hungry"),
            priority = 90,
            description = "Food delivery"
        ),
        "Blinkit" to AppSearchConfig(
            packageName = "com.grofers.customerapp",
            homepageUrl = "https://blinkit.com/",
            limitation = "No public keyword-search deep link.",
            category = "Food", subcategory = "QuickCommerce",
            keywords = listOf("groceries", "instant delivery", "snacks"),
            priority = 75,
            description = "10-minute grocery/food delivery"
        ),
        "Zepto" to AppSearchConfig(
            packageName = "com.zeptoconsumerapp",
            homepageUrl = "https://www.zeptonow.com/",
            limitation = "No public keyword-search deep link.",
            category = "Groceries", subcategory = "QuickCommerce",
            keywords = listOf("groceries", "instant delivery"),
            priority = 70,
            description = "Quick grocery delivery"
        ),
        "BigBasket" to AppSearchConfig(
            packageName = "com.bigbasket.mobileapp",
            webSearchUrl = "https://www.bigbasket.com/ps/?q=",
            homepageUrl = "https://www.bigbasket.com/",
            category = "Groceries", subcategory = "Grocery",
            keywords = listOf("groceries", "vegetables", "household"),
            priority = 72,
            description = "Online grocery"
        ),

        // ===================== SHOPPING =====================
        "Amazon" to AppSearchConfig(
            packageName = "in.amazon.mShop.android.shopping",
            webSearchUrl = "https://www.amazon.in/s?k=",
            homepageUrl = "https://www.amazon.in/",
            category = "Shopping", subcategory = "General",
            keywords = listOf("buy", "shop", "laptop", "phone", "order"),
            priority = 90,
            description = "General e-commerce"
        ),
        "Flipkart" to AppSearchConfig(
            packageName = "com.flipkart.android",
            webSearchUrl = "https://www.flipkart.com/search?q=",
            homepageUrl = "https://www.flipkart.com/",
            category = "Shopping", subcategory = "General",
            keywords = listOf("buy", "shop", "laptop", "phone", "order"),
            priority = 88,
            description = "General e-commerce, India"
        ),
        "Croma" to AppSearchConfig(
            packageName = "com.tatacroma.android",
            homepageUrl = "https://www.croma.com/",
            limitation = "Public search URL not verified. Opens homepage.",
            category = "Shopping", subcategory = "Electronics",
            keywords = listOf("laptop", "phone", "electronics", "gadget", "tv"),
            priority = 75,
            description = "Electronics retail chain, India"
        ),
        "Reliance Digital" to AppSearchConfig(
            packageName = "",
            homepageUrl = "https://www.reliancedigital.in/",
            limitation = "No documented public search URL.",
            category = "Shopping", subcategory = "Electronics",
            keywords = listOf("laptop", "phone", "electronics", "gadget"),
            priority = 72,
            description = "Electronics retail chain, India"
        ),
        "Myntra" to AppSearchConfig(
            packageName = "com.myntra.android",
            webSearchUrl = "https://www.myntra.com/search?q=",
            homepageUrl = "https://www.myntra.com/",
            category = "Shopping", subcategory = "Fashion",
            keywords = listOf("clothes", "fashion", "wear", "shoes"),
            priority = 80,
            description = "Fashion e-commerce"
        ),
        "Ajio" to AppSearchConfig(
            packageName = "com.ril.ajio",
            webSearchUrl = "https://www.ajio.com/search/?text=",
            homepageUrl = "https://www.ajio.com/",
            category = "Shopping", subcategory = "Fashion",
            keywords = listOf("clothes", "fashion", "wear"),
            priority = 75,
            description = "Fashion e-commerce"
        ),
        "Nykaa" to AppSearchConfig(
            packageName = "com.fsn.nykaa",
            webSearchUrl = "https://www.nykaa.com/search/result/?q=",
            homepageUrl = "https://www.nykaa.com/",
            category = "Shopping", subcategory = "Beauty",
            keywords = listOf("makeup", "beauty", "skincare", "cosmetics"),
            priority = 80,
            description = "Beauty and cosmetics"
        ),

        // ===================== EDUCATION =====================
        "Coursera" to AppSearchConfig(
            packageName = "org.coursera.android",
            webSearchUrl = "https://www.coursera.org/search?query=",
            homepageUrl = "https://www.coursera.org/",
            category = "Education", subcategory = "Courses",
            keywords = listOf("study", "learn", "course", "certification"),
            priority = 85,
            description = "University-backed online courses"
        ),
        "Udemy" to AppSearchConfig(
            packageName = "com.udemy.android",
            webSearchUrl = "https://www.udemy.com/courses/search/?q=",
            homepageUrl = "https://www.udemy.com/",
            category = "Education", subcategory = "Courses",
            keywords = listOf("study", "learn", "course"),
            priority = 82,
            description = "Practical skill courses"
        ),
        "edX" to AppSearchConfig(
            packageName = "org.edx.mobile",
            webSearchUrl = "https://www.edx.org/search?q=",
            homepageUrl = "https://www.edx.org/",
            category = "Education", subcategory = "Courses",
            keywords = listOf("study", "learn", "course", "university"),
            priority = 78,
            description = "University-backed online courses"
        ),
        "freeCodeCamp" to AppSearchConfig(
            packageName = "",
            webSearchUrl = "https://www.freecodecamp.org/news/search/?query=",
            homepageUrl = "https://www.freecodecamp.org/",
            category = "Education", subcategory = "Programming",
            keywords = listOf("coding", "programming", "web development"),
            priority = 80,
            description = "Free coding curriculum"
        ),
        "Khan Academy" to AppSearchConfig(
            packageName = "org.khanacademy.android",
            webSearchUrl = "https://www.khanacademy.org/search?page_search_query=",
            homepageUrl = "https://www.khanacademy.org/",
            category = "Education", subcategory = "School",
            keywords = listOf("study", "school", "math", "science", "fundamentals"),
            priority = 78,
            description = "Free K-12 fundamentals"
        ),
        "LeetCode" to AppSearchConfig(
            packageName = "",
            webSearchUrl = "https://leetcode.com/problemset/?search=",
            homepageUrl = "https://leetcode.com/problemset/",
            category = "Education", subcategory = "Programming",
            keywords = listOf("coding", "dsa", "interview prep", "leetcode"),
            priority = 82,
            description = "Coding interview practice"
        ),
        "HackerRank" to AppSearchConfig(
            packageName = "",
            homepageUrl = "https://www.hackerrank.com/",
            limitation = "Public search URL not verified. Opens homepage.",
            category = "Education", subcategory = "Programming",
            keywords = listOf("coding", "dsa", "skill test"),
            priority = 70,
            description = "Coding practice and skill certification"
        ),

        // ===================== VIDEO / MUSIC =====================
        "YouTube" to AppSearchConfig(
            packageName = "com.google.android.youtube",
            webSearchUrl = "https://www.youtube.com/results?search_query=",
            homepageUrl = "https://www.youtube.com/",
            category = "Entertainment", subcategory = "Video",
            keywords = listOf("video", "tutorial", "watch", "music video"),
            priority = 85,
            description = "Video platform"
        ),
        "Spotify" to AppSearchConfig(
            packageName = "com.spotify.music",
            uriScheme = "spotify:search:{query}",
            webSearchUrl = "https://open.spotify.com/search/",
            homepageUrl = "https://open.spotify.com/",
            category = "Entertainment", subcategory = "Music",
            keywords = listOf("music", "song", "artist", "playlist"),
            priority = 88,
            description = "Music streaming"
        ),
        "Netflix" to AppSearchConfig(
            packageName = "com.netflix.mediaclient",
            homepageUrl = "https://www.netflix.com/browse",
            limitation = "No public keyword-search deep link.",
            category = "Entertainment", subcategory = "Movies",
            keywords = listOf("movie", "show", "series", "watch"),
            priority = 80,
            description = "Streaming movies/shows"
        ),
        "Prime Video" to AppSearchConfig(
            packageName = "com.amazon.avod.thirdpartyclient",
            homepageUrl = "https://www.primevideo.com/",
            limitation = "No public keyword-search deep link.",
            category = "Entertainment", subcategory = "Movies",
            keywords = listOf("movie", "show", "series"),
            priority = 75,
            description = "Streaming movies/shows"
        ),
        "Disney+ Hotstar" to AppSearchConfig(
            packageName = "in.startv.hotstar",
            homepageUrl = "https://www.hotstar.com/in",
            limitation = "No public keyword-search deep link.",
            category = "Entertainment", subcategory = "Movies",
            keywords = listOf("movie", "show", "cricket", "sports streaming"),
            priority = 75,
            description = "Streaming, sports and shows, India"
        ),

        // ===================== FINANCE =====================
        "Paytm" to AppSearchConfig(
            packageName = "net.one97.paytm",
            homepageUrl = "https://paytm.com/",
            limitation = "No public keyword-search deep link.",
            category = "Finance", subcategory = "Payments",
            keywords = listOf("pay", "recharge", "bill payment", "upi"),
            priority = 80,
            description = "Payments and recharges"
        ),
        "PhonePe" to AppSearchConfig(
            packageName = "com.phonepe.app",
            homepageUrl = "https://www.phonepe.com/",
            limitation = "No public keyword-search deep link.",
            category = "Finance", subcategory = "Payments",
            keywords = listOf("pay", "upi", "recharge"),
            priority = 82,
            description = "UPI payments"
        ),
        "Google Pay" to AppSearchConfig(
            packageName = "com.google.android.apps.nbu.paisa.user",
            homepageUrl = "https://pay.google.com/",
            limitation = "No public keyword-search deep link.",
            category = "Finance", subcategory = "Payments",
            keywords = listOf("pay", "upi", "recharge"),
            priority = 82,
            description = "UPI payments"
        ),
        "Zerodha" to AppSearchConfig(
            packageName = "com.zerodha.kite3",
            homepageUrl = "https://kite.zerodha.com/",
            limitation = "No public keyword-search deep link.",
            category = "Finance", subcategory = "Investing",
            keywords = listOf("stocks", "trading", "invest", "mutual fund"),
            priority = 78,
            description = "Stock trading and investing"
        ),
        "Groww" to AppSearchConfig(
            packageName = "com.nextbillion.groww",
            homepageUrl = "https://groww.in/",
            limitation = "No public keyword-search deep link.",
            category = "Finance", subcategory = "Investing",
            keywords = listOf("stocks", "mutual fund", "invest"),
            priority = 76,
            description = "Investing and mutual funds"
        ),

        // ===================== HEALTH =====================
        "Practo" to AppSearchConfig(
            packageName = "com.practo.fabric",
            webSearchUrl = "https://www.practo.com/search?q=",
            homepageUrl = "https://www.practo.com/",
            category = "Health", subcategory = "Medicine",
            keywords = listOf("doctor", "appointment", "medicine", "clinic"),
            priority = 78,
            description = "Doctor appointments and health"
        ),
        "1mg" to AppSearchConfig(
            packageName = "com.healthkartplus.healthkartplus",
            webSearchUrl = "https://www.1mg.com/search/all?name=",
            homepageUrl = "https://www.1mg.com/",
            category = "Health", subcategory = "Medicine",
            keywords = listOf("medicine", "pharmacy", "medical test"),
            priority = 78,
            description = "Online pharmacy"
        ),
        "Strong" to AppSearchConfig(
            packageName = "",
            homepageUrl = "https://www.strong.app/",
            category = "Health", subcategory = "Fitness",
            keywords = listOf("workout", "gym", "lift", "exercise"),
            priority = 72,
            description = "Workout tracking"
        ),
        "Hevy" to AppSearchConfig(
            packageName = "com.hevy",
            homepageUrl = "https://www.hevyapp.com/",
            category = "Health", subcategory = "Fitness",
            keywords = listOf("workout", "gym", "lift"),
            priority = 72,
            description = "Workout tracking"
        ),
        "MyFitnessPal" to AppSearchConfig(
            packageName = "com.myfitnesspal.android",
            webSearchUrl = "https://www.myfitnesspal.com/food/search?search=",
            homepageUrl = "https://www.myfitnesspal.com/",
            category = "Health", subcategory = "Nutrition",
            keywords = listOf("nutrition", "calories", "protein", "diet"),
            priority = 75,
            description = "Nutrition tracking"
        ),

        // ===================== REAL ESTATE =====================
        "99acres" to AppSearchConfig(
            packageName = "com.til.nineninenine",
            webSearchUrl = "https://www.99acres.com/search/property/buy?keyword=",
            homepageUrl = "https://www.99acres.com/",
            category = "RealEstate", subcategory = "Property",
            keywords = listOf("rent", "flat", "property", "real estate", "house"),
            priority = 75,
            description = "Property listings, India"
        ),
        "MagicBricks" to AppSearchConfig(
            packageName = "com.magicbricks.mbrealty",
            homepageUrl = "https://www.magicbricks.com/",
            limitation = "Search requires city/locality IDs. Opens homepage.",
            category = "RealEstate", subcategory = "Property",
            keywords = listOf("rent", "flat", "property", "real estate"),
            priority = 73,
            description = "Property listings, India"
        ),

        // ===================== JOBS =====================
        "LinkedIn" to AppSearchConfig(
            packageName = "com.linkedin.android",
            webSearchUrl = "https://www.linkedin.com/search/results/all/?keywords=",
            homepageUrl = "https://www.linkedin.com/",
            category = "Jobs", subcategory = "Professional",
            keywords = listOf("job", "career", "networking", "resume", "hiring"),
            priority = 85,
            description = "Professional networking and jobs"
        ),
        "Naukri" to AppSearchConfig(
            packageName = "naukriapp.naukri.com",
            webSearchUrl = "https://www.naukri.com/",
            homepageUrl = "https://www.naukri.com/",
            limitation = "Search URL requires structured params. Opens homepage.",
            category = "Jobs", subcategory = "JobSearch",
            keywords = listOf("job", "hiring", "career", "resume"),
            priority = 78,
            description = "Job search, India"
        ),
        "Indeed" to AppSearchConfig(
            packageName = "com.indeed.android.jobsearch",
            webSearchUrl = "https://www.indeed.com/jobs?q=",
            homepageUrl = "https://www.indeed.com/",
            category = "Jobs", subcategory = "JobSearch",
            keywords = listOf("job", "hiring", "career"),
            priority = 78,
            description = "Job search"
        ),

        // ===================== NEWS =====================
        "Google News" to AppSearchConfig(
            packageName = "com.google.android.apps.magazines",
            webSearchUrl = "https://news.google.com/search?q=",
            homepageUrl = "https://news.google.com/",
            category = "News", subcategory = "General",
            keywords = listOf("news", "headlines", "current affairs"),
            priority = 75,
            description = "News aggregator"
        ),

        // ===================== DATING =====================
        "Tinder" to AppSearchConfig(
            packageName = "com.tinder",
            homepageUrl = "https://tinder.com/",
            limitation = "No keyword-search concept — dating apps use profile matching, not search.",
            category = "Dating", subcategory = "General",
            keywords = listOf("dating", "match", "relationship"),
            priority = 70,
            description = "Dating and matchmaking"
        ),

        // ===================== SOCIAL / CONTENT =====================
        "Instagram" to AppSearchConfig(
            packageName = "com.instagram.android",
            homepageUrl = "https://www.instagram.com/",
            limitation = "Only hashtag/username/location deep links exist, not free-text search.",
            category = "SocialMedia", subcategory = "General",
            keywords = listOf("social", "photos", "reels"),
            priority = 70,
            description = "Photo/video social network"
        ),
        "Reddit" to AppSearchConfig(
            packageName = "com.reddit.frontpage",
            webSearchUrl = "https://www.reddit.com/search/?q=",
            homepageUrl = "https://www.reddit.com/",
            category = "SocialMedia", subcategory = "Forums",
            keywords = listOf("forum", "discussion", "community"),
            priority = 70,
            description = "Community discussion forums"
        ),
        "Quora" to AppSearchConfig(
            packageName = "com.quora.android",
            webSearchUrl = "https://www.quora.com/search?q=",
            homepageUrl = "https://www.quora.com/",
            category = "SocialMedia", subcategory = "QandA",
            keywords = listOf("question", "answer", "advice"),
            priority = 68,
            description = "Q&A community"
        ),
        "Wikipedia" to AppSearchConfig(
            packageName = "org.wikipedia",
            webSearchUrl = "https://en.wikipedia.org/w/index.php?search=",
            homepageUrl = "https://en.wikipedia.org/",
            category = "General", subcategory = "Reference",
            keywords = listOf("wiki", "encyclopedia", "reference"),
            priority = 70,
            description = "Free encyclopedia"
        ),

        // ===================== DEV / PRODUCTIVITY =====================
        "GitHub" to AppSearchConfig(
            packageName = "com.github.android",
            webSearchUrl = "https://github.com/search?q=",
            homepageUrl = "https://github.com/",
            category = "Developer", subcategory = "Code",
            keywords = listOf("code", "repository", "github", "open source"),
            priority = 82,
            description = "Code hosting and version control"
        ),
        "Stack Overflow" to AppSearchConfig(
            packageName = "",
            webSearchUrl = "https://stackoverflow.com/search?q=",
            homepageUrl = "https://stackoverflow.com/",
            category = "Developer", subcategory = "QandA",
            keywords = listOf("coding error", "programming question", "debug"),
            priority = 78,
            description = "Developer Q&A"
        ),

        // ===================== UTILITIES =====================
        "Google Maps" to AppSearchConfig(
            packageName = "com.google.android.apps.maps",
            webSearchUrl = "https://www.google.com/maps/search/",
            homepageUrl = "https://www.google.com/maps",
            category = "Navigation", subcategory = "Maps",
            keywords = listOf("directions", "near me", "location", "map"),
            priority = 90,
            description = "Maps and navigation"
        ),
        "Play Store" to AppSearchConfig(
            packageName = "com.android.vending",
            webSearchUrl = "https://play.google.com/store/search?q=",
            homepageUrl = "https://play.google.com/store",
            category = "Utilities", subcategory = "AppStore",
            keywords = listOf("app store", "download app"),
            priority = 60,
            description = "Android app store"
        ),
        "Chrome" to AppSearchConfig(
            packageName = "com.android.chrome",
            webSearchUrl = "https://www.google.com/search?q=",
            homepageUrl = "https://www.google.com/",
            category = "Utilities", subcategory = "Browser",
            keywords = listOf("browser", "web search"),
            priority = 50,
            description = "Web browser"
        ),
        "Google Search" to AppSearchConfig(
            packageName = "",
            webSearchUrl = "https://www.google.com/search?q=",
            homepageUrl = "https://www.google.com/",
            category = "General", subcategory = "Search",
            keywords = emptyList(),
            priority = 10,
            description = "General web search fallback"
        )
    )

    fun appsInCategory(category: String): List<Pair<String, AppSearchConfig>> =
        apps.filter { it.value.category.equals(category, ignoreCase = true) }
            .toList()
            .sortedByDescending { it.second.priority }

    fun appsInSubcategory(category: String, subcategory: String): List<Pair<String, AppSearchConfig>> =
        apps.filter {
            it.value.category.equals(category, ignoreCase = true) &&
                    it.value.subcategory.equals(subcategory, ignoreCase = true)
        }.toList().sortedByDescending { it.second.priority }
}