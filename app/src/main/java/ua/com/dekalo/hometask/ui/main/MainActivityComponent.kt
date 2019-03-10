package ua.com.dekalo.hometask.ui.main

import dagger.Component
import ua.com.dekalo.hometask.domain.DomainModule
import ua.com.dekalo.hometask.domain.PostsRepository
import ua.com.dekalo.hometask.network.NetworkModule

@Component(modules = [NetworkModule::class, DomainModule::class])
interface MainActivityComponent {
    fun getPostsApi(): PostsRepository
}