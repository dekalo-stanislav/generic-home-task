package ua.com.dekalo.hometask.ui.details

import dagger.Component
import ua.com.dekalo.hometask.AppComponent
import ua.com.dekalo.hometask.ui.PerActivity

@PerActivity
@Component(modules = [DetailsActivityViewModelModule::class], dependencies = [AppComponent::class])
interface DetailsActivityComponent {
    fun inject(activity: DetailsActivity)
}