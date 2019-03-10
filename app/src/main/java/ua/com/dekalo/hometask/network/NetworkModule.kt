package ua.com.dekalo.hometask.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.com.dekalo.hometask.BuildConfig


@Module
class NetworkModule {

    @Provides
    fun providePostsApi(): PostsApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }
}