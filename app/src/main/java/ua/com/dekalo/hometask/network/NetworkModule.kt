package ua.com.dekalo.hometask.network

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.com.dekalo.hometask.BuildConfig
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        fun createLoggingInterceptor(): HttpLoggingInterceptor {
            val logger = HttpLoggingInterceptor.Logger { message -> Log.d("networking", message) }
            return HttpLoggingInterceptor(logger)
                .apply { level = HttpLoggingInterceptor.Level.BODY }
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {

        return if (BuildConfig.HTTP_LOGGING_ENABLED) {
            OkHttpClient.Builder()
                .addInterceptor(createLoggingInterceptor())
                .build()
        } else {
            OkHttpClient()
        }
    }

    @Singleton
    @Provides
    fun providePostsApi(client: OkHttpClient): ServiceApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }
}