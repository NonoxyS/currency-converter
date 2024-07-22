package dev.nonoxy.currencyconverter.api.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CurrencyDTO(
    @SerialName("base") val baseCode: String,
    @SerialName("target") val targetCode: String,
    @SerialName("base_amount") val baseAmount: Float,
    @SerialName("converted_amount") val convertedAmount: Float,
    @SerialName("exchange_rate") val exchangeRate: Float,
    @SerialName("last_updated") val date: Long
)