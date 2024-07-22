package dev.nonoxy.currencyconverter.features.currencyconverter.domain.usecases

import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.data.map
import dev.nonoxy.currencyconverter.data.network.CurrencyConverterDataSource
import dev.nonoxy.currencyconverter.data.network.models.Currency
import dev.nonoxy.currencyconverter.features.currencyconverter.domain.models.CurrencyConversionUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val currencyConverterDataSource: CurrencyConverterDataSource
) {
    internal fun execute(baseCode: String, targetCode: String, amount: String): Flow<RequestResult<CurrencyConversionUI>> {
        return currencyConverterDataSource.convertCurrency(
            baseCode = baseCode,
            targetCode = targetCode,
            amount = amount
        ).map { result -> result.map { it.toCurrencyConverterUI() } }.flowOn(Dispatchers.Default)
    }
}

private fun Currency.toCurrencyConverterUI(): CurrencyConversionUI {
    return CurrencyConversionUI(
        baseCode = baseCode,
        targetCode = targetCode,
        baseAmount = baseAmount.toString(),
        convertedAmount = convertedAmount.toString(),
        conversionRate = exchangeRate.toString()
    )
}