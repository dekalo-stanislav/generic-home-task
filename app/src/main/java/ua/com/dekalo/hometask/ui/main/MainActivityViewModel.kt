package ua.com.dekalo.hometask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.com.dekalo.hometask.domain.GenericRepository
import ua.com.dekalo.hometask.domain.PostsRepository
import ua.com.dekalo.hometask.ui.utils.NonnullMutableLiveData
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val postsRepository: PostsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _mainData = NonnullMutableLiveData(MainActivityData())
    val data: LiveData<MainActivityData> get() = _mainData

    fun loadData(allowCache: Boolean = true) {
        compositeDisposable.add(
            postsRepository.load(GenericRepository.createGenericSpec(allowCache = allowCache))
                .doOnSubscribe { changeViewModelState { it.copy(isLoading = true, error = null) } }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { posts -> changeViewModelState { it.copy(posts = posts, isLoading = false, error = null) } },
                    { exception -> changeViewModelState { it.copy(error = exception, isLoading = false) } })
        )
    }

    private fun changeViewModelState(change: (MainActivityData) -> MainActivityData) {
        _mainData.postValue(change.invoke(_mainData.value))
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
