package dev.nonoxy.currencyconverter.features.theme.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkPalette else LightPalette

    CompositionLocalProvider(
        LocalCurrencyConverterColor provides colorScheme,
        LocalCurrencyConverterTypography provides Typography,
        content = {
            Box(
                modifier = Modifier
                    .background(CurrencyConverterTheme.colors.primaryBackground)
                    .fillMaxSize()
                    .safeDrawingPadding()
            ) {
                content.invoke()
            }
        }
    )
}

object CurrencyConverterTheme {
    val colors: CurrencyConverterColors
        @Composable
        get() = LocalCurrencyConverterColor.current
    val typography: CurrencyConverterTypography
        @Composable
        get() = LocalCurrencyConverterTypography.current
}