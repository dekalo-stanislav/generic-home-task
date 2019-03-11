package ua.com.dekalo.hometask.domain

import dagger.Module
import dagger.Provides
import ua.com.dekalo.hometask.cache.CommentsCache
import ua.com.dekalo.hometask.cache.PostsCache
import ua.com.dekalo.hometask.network.ServiceApi
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun providesPostsRepository(api: ServiceApi, postsCache: PostsCache): PostsRepository {
        return DefaultPostsRepository(api, postsCache)
    }

    @Singleton
    @Provides
    fun providesCommentsRepository(api: ServiceApi, commentsCache: CommentsCache): CommentsRepository {
        return DefaultCommentsRepository(api, commentsCache)
    }
}