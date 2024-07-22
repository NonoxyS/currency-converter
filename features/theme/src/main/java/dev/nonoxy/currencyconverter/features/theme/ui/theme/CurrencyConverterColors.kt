package dev.nonoxy.currencyconverter.features.theme.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CurrencyConverterColors(
    val primaryText: Color,
    val primaryBackground: Color,
)

internal val LightPalette = CurrencyConverterColors(
    primaryText = Color.Black,
    primaryBackground = Color.White,
)

internal val DarkPalette = CurrencyConverterColors(
    primaryText = Color.White,
    primaryBackground = Color.Black,

)

internal val LocalCurrencyConverterColor =
    staticCompositionLocalOf<CurrencyConverterColors> { error("No default implementation for colors") }