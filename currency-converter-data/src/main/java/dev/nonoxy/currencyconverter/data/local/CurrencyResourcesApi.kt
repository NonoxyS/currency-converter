package dev.nonoxy.currencyconverter.api.local

import dev.nonoxy.currencyconverter.api.local.models.CurrencyInfoDTO

interface CurrencyResourcesApi {
    suspend fun getCurrencyList(): Result<List<CurrencyInfoDTO>>
}

fun CurrencyResourcesApi(currencyJsonString: String): CurrencyResourcesApi {
    return CurrencyResourcesApi(currencyJsonString)
}