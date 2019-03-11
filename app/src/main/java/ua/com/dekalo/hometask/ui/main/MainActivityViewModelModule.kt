package ua.com.dekalo.hometask.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ua.com.dekalo.hometask.ui.ViewModelKey
import ua.com.dekalo.hometask.ui.ViewModelModule

@Module(includes = [ViewModelModule::class])
abstract class MainActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(model: MainActivityViewModel): ViewModel
}