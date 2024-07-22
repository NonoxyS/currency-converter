package dev.nonoxy.restaurant_reviews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nonoxy.restaurant_reviews.BuildConfig
import dev.nonoxy.restaurant_reviews.api.RestaurantReviewsApi
import dev.nonoxy.restaurant_reviews.common.eventbus.EventBusController
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideRestaurantReviewsApi(okHttpClient: OkHttpClient?): RestaurantReviewsApi {
        return RestaurantReviewsApi(
            baseUrl = BuildConfig.RESTAURANT_REVIEWS_API_BASE_URL,
            apiKey = BuildConfig.RESTAURANT_REVIEWS_API_KEY,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideEventBusController(): EventBusController {
        return EventBusController()
    }
}