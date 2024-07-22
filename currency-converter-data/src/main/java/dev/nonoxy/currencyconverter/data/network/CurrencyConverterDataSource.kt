package dev.nonoxy.currencyconverter.data.network

import dev.nonoxy.currencyconverter.api.CurrencyConverterApi
import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.data.map
import dev.nonoxy.currencyconverter.data.network.models.Currency
import dev.nonoxy.currencyconverter.data.toRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CurrencyConverterDataSource @Inject constructor(
    private val currencyConverterApi: CurrencyConverterApi
) {
    fun convertCurrency(
        baseCode: String,
        targetCode: String,
        amount: String
    ): Flow<RequestResult<Currency>> {
        val apiRequest = flow {
            emit(
                currencyConverterApi.getConvertedCurrency(
                    baseCurrencyCode = baseCode,
                    targetCurrencyCode = targetCode,
                    baseAmount = amount
                )
            )
        }.flowOn(Dispatchers.IO)
            .map { result ->
                result.toRequestResult().map { it.toCurrency() }
            }.flowOn(Dispatchers.Default)


        val start = flowOf<RequestResult<Currency>>(RequestResult.InProgress())

        return merge(apiRequest, start)
    }
}