package ua.com.dekalo.hometask.cache

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun providePostsCache(): PostsCache {
        return PostsCache()
    }

    @Singleton
    @Provides
    fun provideCommentsCache(): CommentsCache {
        return CommentsCache()
    }
}