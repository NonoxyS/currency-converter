package dev.nonoxy.currencyconverter.data.network

import dev.nonoxy.currencyconverter.api.models.CurrencyDTO
import dev.nonoxy.currencyconverter.data.network.models.Currency

internal fun CurrencyDTO.toCurrency(): Currency {
    return Currency(
        baseCode = baseCode,
        targetCode = targetCode,
        baseAmount = baseAmount,
        convertedAmount = convertedAmount,
        exchangeRate = exchangeRate,
        date = date,
    )
}