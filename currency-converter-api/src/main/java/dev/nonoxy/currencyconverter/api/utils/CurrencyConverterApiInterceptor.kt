package dev.nonoxy.currencyconverter.api.network.utils

import okhttp3.Interceptor
import okhttp3.Response

internal class CurrencyConverterApiInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        return chain.proceed(
            chain.request().newBuilder()
                .url(newUrl)
                .build()
        )
    }
}