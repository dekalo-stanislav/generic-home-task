package ua.com.dekalo.hometask.ui.details

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ua.com.dekalo.hometask.ui.ViewModelKey
import ua.com.dekalo.hometask.ui.ViewModelModule

@Module(includes = [ViewModelModule::class])
abstract class DetailsActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsActivityViewModel::class)
    abstract fun bindMainActivityViewModel(model: DetailsActivityViewModel): ViewModel
}