package ua.com.dekalo.hometask.ui.details

import dagger.Component
import ua.com.dekalo.hometask.domain.CommentsRepository
import ua.com.dekalo.hometask.domain.DomainModule
import ua.com.dekalo.hometask.network.NetworkModule

@Component(modules = [NetworkModule::class, DomainModule::class, DetailsActivityViewModelModule::class])
interface DetailsActivityComponent {
    fun inject(activity: DetailsActivity)
}