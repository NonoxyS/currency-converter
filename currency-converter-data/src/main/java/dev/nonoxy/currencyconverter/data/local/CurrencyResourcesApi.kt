package dev.nonoxy.currencyconverter.data.local

import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.data.local.models.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyResourcesApi {
    fun getCurrencyList(): Flow<RequestResult<List<CurrencyInfo>>>
}