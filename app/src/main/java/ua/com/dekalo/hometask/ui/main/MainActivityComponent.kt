package ua.com.dekalo.hometask.ui.main

import dagger.Component
import ua.com.dekalo.hometask.AppComponent
import ua.com.dekalo.hometask.ui.PerActivity

@PerActivity
@Component(modules = [MainActivityViewModelModule::class], dependencies = [AppComponent::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}