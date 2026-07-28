package com.example.vedamo.ui.theme

import androidx.compose.ui.graphics.Color

// Base neutrals
val BackgroundLight = Color(0xFFF8FAFC)
val BackgroundDark = Color(0xFF0F172A)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFF1E293B)
val ErrorRed = Color(0xFFDC2626)
val TextDark = Color(0xFF0F172A)
val TextLight = Color(0xFFF8FAFC)

// Fallback single colors (kept for compatibility)
val PrimaryBlue = Color(0xFF2563EB)
val PrimaryBlueDark = Color(0xFF1E40AF)
val AccentTeal = Color(0xFF14B8A6)

data class GradientPalette(
    val start: Color,
    val end: Color
)

val vibrantPalettes = listOf(
    GradientPalette(Color(0xFF7F00FF), Color(0xFFE100FF)), // purple to pink
    GradientPalette(Color(0xFFFF512F), Color(0xFFF09819)), // red to orange
    GradientPalette(Color(0xFF00C6FF), Color(0xFF0072FF)), // sky to blue
    GradientPalette(Color(0xFF11998E), Color(0xFF38EF7D)), // teal to green
    GradientPalette(Color(0xFFFC466B), Color(0xFF3F5EFB)), // pink to indigo
    GradientPalette(Color(0xFFF953C6), Color(0xFFB91D73)), // magenta to deep pink
    GradientPalette(Color(0xFFFF9A9E), Color(0xFFFAD0C4)), // soft coral
    GradientPalette(Color(0xFF00F5A0), Color(0xFF00D9F5)), // mint to cyan
    GradientPalette(Color(0xFFFDBB2D), Color(0xFF22C1C3)), // gold to teal
    GradientPalette(Color(0xFF8E2DE2), Color(0xFF4A00E0))  // violet to deep purple
)