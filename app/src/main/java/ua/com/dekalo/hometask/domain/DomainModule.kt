package ua.com.dekalo.hometask.domain

import dagger.Module
import dagger.Provides
import ua.com.dekalo.hometask.network.ServiceApi

@Module
class DomainModule {

    @Provides
    fun providesPostsRepository(api: ServiceApi): PostsRepository {
        return DefaultPostsRepository(api)
    }

    @Provides
    fun providesCommentsRepository(api: ServiceApi): CommentsRepository {
        return DefaultCommentsRepository(api)
    }
}