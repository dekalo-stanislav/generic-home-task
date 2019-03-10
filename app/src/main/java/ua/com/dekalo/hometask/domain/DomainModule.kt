package ua.com.dekalo.hometask.domain

import dagger.Module
import dagger.Provides
import ua.com.dekalo.hometask.network.PostsApi

@Module
class DomainModule {

    @Provides
    fun providesPostsRepository(api: PostsApi): PostsRepository {
        return DefaultPostsRepository(api)
    }
}