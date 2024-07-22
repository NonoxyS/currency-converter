package dev.nonoxy.currencyconverter.features.theme.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class CurrencyConverterTypography(
    val titleLarge: TextStyle
)

internal val Typography = CurrencyConverterTypography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
    )
)

val LocalCurrencyConverterTypography =
    staticCompositionLocalOf<CurrencyConverterTypography> { error("No default implementation for fonts") }