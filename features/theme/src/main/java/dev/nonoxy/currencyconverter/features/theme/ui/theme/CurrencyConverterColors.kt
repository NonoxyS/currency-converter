package dev.nonoxy.currencyconverter.features.theme.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CurrencyConverterColors(
    val primaryText: Color,
    val secondaryText: Color,
    val primaryBackground: Color,
    val primaryContainer: Color,
    val primaryButton: Color,
    val onPrimaryButton: Color,
)

internal val LightPalette = CurrencyConverterColors(
    primaryText = Color(0xFF_0D171C),
    secondaryText = Color(0xFF_4A7D9C),
    primaryBackground = Color(0xFF_FCF7FA),
    primaryContainer = Color(0xFF_E8F0F5),
    primaryButton = Color(0xFF_0D82C9),
    onPrimaryButton = Color(0xFF_F7FAFC),
)

internal val DarkPalette = CurrencyConverterColors(
    primaryText = Color.White,
    secondaryText = Color(0xFF_8FB5CC),
    primaryBackground = Color(0xFF_0F1C24),
    primaryContainer = Color(0xFF_213B4A),
    primaryButton = Color(0xFF_0D82C9),
    onPrimaryButton = Color(0xFF_FFFFFF),
)

internal val LocalCurrencyConverterColor =
    staticCompositionLocalOf<CurrencyConverterColors> { error("No default implementation for colors") }