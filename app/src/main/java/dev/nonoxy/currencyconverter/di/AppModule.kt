package dev.nonoxy.currencyconverter.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.nonoxy.currencyconverter.BuildConfig
import dev.nonoxy.currencyconverter.R
import dev.nonoxy.currencyconverter.api.CurrencyConverterApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.io.InputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient? {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        return null
    }

    @Provides
    @Singleton
    fun provideCurrencyConverterApi(okHttpClient: OkHttpClient?): CurrencyConverterApi {
        return CurrencyConverterApi(
            baseUrl = BuildConfig.CURRENCY_CONVERTER_API_BASE_URL,
            apiKey = BuildConfig.CURRENCY_CONVERTER_API_KEY,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideCurrencyResourceByteArray(@ApplicationContext context: Context): ByteArray? {
        try {
            val inputStream = context.assets.open("currency_list.json")
            val byteArray = inputStream.readBytes().also { inputStream.close() }
            return byteArray
        } catch (ioException: IOException) {
            return null
        }
    }

}