package dev.nonoxy.currencyconverter.features.currencyselector.domain.usecases

import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.data.local.CurrencyResourcesDataSource
import dev.nonoxy.currencyconverter.data.local.models.CurrencyInfo
import dev.nonoxy.currencyconverter.data.map
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyInfoUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrencyResourcesInfoUseCase @Inject constructor(
    private val currencyResourcesDataSource: CurrencyResourcesDataSource
) {
    internal fun execute(): Flow<RequestResult<List<CurrencyInfoUI>>> {
        return currencyResourcesDataSource.getCurrencyList()
            .map { result ->
                result.map { currencyInfoList -> currencyInfoList.map { it.toCurrencyInfoUI() } }
            }.flowOn(Dispatchers.Default)
    }
}

private fun CurrencyInfo.toCurrencyInfoUI(): CurrencyInfoUI {
    return CurrencyInfoUI(
        currencyCode = currencyCode,
        currencyName = currencyName,
        currencySymbol = currencySymbol,
        countryCode = countryCode
    )
}