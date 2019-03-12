package ua.com.dekalo.hometask

import dagger.Component
import ua.com.dekalo.hometask.domain.CommentsRepository
import ua.com.dekalo.hometask.domain.DomainModule
import ua.com.dekalo.hometask.domain.PostsRepository
import ua.com.dekalo.hometask.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, NetworkModule::class])
interface AppComponent {
    fun commentsRepository(): CommentsRepository
    fun postsRepository(): PostsRepository
}