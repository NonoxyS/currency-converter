package dev.nonoxy.currencyconverter.features.currencyselector.domain.models

class ConversionCurrencyInfo(
    val baseCurrencyCode: String,
    val targetCurrencyCode: String,
    val amount: String
)