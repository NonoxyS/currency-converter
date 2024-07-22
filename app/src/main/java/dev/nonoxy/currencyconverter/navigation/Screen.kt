package dev.nonoxy.currencyconverter.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object CurrencySelector : Screen()
    @Serializable
    data class CurrencyConversionResult(
        val baseCurrencyCode: String,
        val targetCurrencyCode: String,
        val amount: String
    ) : Screen()
}