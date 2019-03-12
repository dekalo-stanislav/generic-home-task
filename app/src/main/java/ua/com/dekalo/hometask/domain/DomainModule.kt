package ua.com.dekalo.hometask.domain

import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun providesPostsRepository(defaultPostsRepository: DefaultPostsRepository): PostsRepository {
        return defaultPostsRepository
    }

    @Provides
    fun providesCommentsRepository(defaultCommentsRepository: DefaultCommentsRepository): CommentsRepository {
        return defaultCommentsRepository
    }
}