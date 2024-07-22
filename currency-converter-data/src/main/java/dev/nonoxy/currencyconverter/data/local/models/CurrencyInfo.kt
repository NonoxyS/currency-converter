package dev.nonoxy.currencyconverter.data.local.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CurrencyInfo(
    @SerialName("currency_code") val currencyCode: String,
    @SerialName("currency_name") val currencyName: String,
    @SerialName("currency_symbol") val currencySymbol: String,
    @SerialName("country_code") val countryCode: String
)