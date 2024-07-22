package dev.nonoxy.currencyconverter

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrencyConverterApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            .crossfade(true)
            .build()
    }
}