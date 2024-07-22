package dev.nonoxy.currencyconverter.features.currencyconverter.domain.models

data class CurrencyConversionUI(
    val baseCode: String,
    val targetCode: String,
    val baseAmount: String,
    val convertedAmount: String,
    val conversionRate: String
)