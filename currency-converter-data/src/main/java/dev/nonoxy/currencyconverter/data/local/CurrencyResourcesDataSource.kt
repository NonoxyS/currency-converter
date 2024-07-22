package dev.nonoxy.currencyconverter.data.local

import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.data.local.models.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CurrencyResourcesDataSource @Inject constructor(
    private val currencyInfoByteArray: ByteArray?
) : CurrencyResourcesApi {
    private val json = Json { coerceInputValues = true }

    override fun getCurrencyList(): Flow<RequestResult<List<CurrencyInfo>>> {
        return flow {
            emit(RequestResult.InProgress())
            try {
                if (currencyInfoByteArray == null) {
                    emit(RequestResult.Error(Throwable("ByteArray is null")))
                } else {
                    val decodedString = json.decodeFromString<List<CurrencyInfo>>(
                        currencyInfoByteArray.decodeToString()
                    )
                    emit(RequestResult.Success(decodedString))
                }
            } catch (e: Exception) {
                emit(RequestResult.Error(e))
            }
        }
    }
}