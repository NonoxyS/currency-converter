package dev.nonoxy.currencyconverter.api.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dev.nonoxy.currencyconverter.api.network.models.CurrencyDTO
import dev.nonoxy.currencyconverter.api.network.utils.CurrencyConverterApiInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApi {
    @GET("convert")
    suspend fun getConvertedCurrency(
        @Query("base") baseCurrencyCode: String,
        @Query("target") targetCurrencyCode: String,
        @Query("base_amount") baseAmount: Float
    ): Result<CurrencyDTO>

}

fun CurrencyConverterApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null
): CurrencyConverterApi {
    return retrofit(baseUrl, apiKey, okHttpClient).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?
): Retrofit {
    val json = Json { ignoreUnknownKeys = true }
    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(CurrencyConverterApiInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .addConverterFactory(jsonConverterFactory)
        .client(modifiedOkHttpClient)
        .build()
}