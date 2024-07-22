package dev.nonoxy.currencyconverter.data.network.models

class Currency(
    val baseCode: String,
    val targetCode: String,
    val baseAmount: Float,
    val convertedAmount: Float,
    val exchangeRate: Float,
    val date: Long
)