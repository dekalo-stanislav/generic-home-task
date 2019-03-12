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

    private val _state = NonnullMutableLiveData<MainActivityState>(MainActivityState())
    val state: LiveData<MainActivityState> get() = _state

    fun loadData(allowCache: Boolean = true) {
        compositeDisposable.add(
            postsRepository.load(GenericRepository.createGenericSpec(allowCache = allowCache))
                .doOnSubscribe { changeState { it.copy(isLoading = true, error = null) } }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { posts -> changeState { it.copy(posts = posts, isLoading = false, error = null) } },
                    { exception -> changeState { it.copy(error = exception, isLoading = false) } })
        )
    }

    private fun changeState(change: (MainActivityState) -> MainActivityState) {
        _state.postValue(change.invoke(_state.value))
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
