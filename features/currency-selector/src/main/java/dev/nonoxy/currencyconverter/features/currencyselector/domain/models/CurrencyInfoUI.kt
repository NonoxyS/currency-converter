package dev.nonoxy.currencyconverter.features.currencyselector.domain.models

data class CurrencyInfoUI(
    val currencyCode: String,
    val currencyName: String,
    val currencySymbol: String,
    val countryCode: String
)