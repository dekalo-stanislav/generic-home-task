package ua.com.dekalo.hometask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.com.dekalo.hometask.domain.GenericRepository
import ua.com.dekalo.hometask.domain.PostsRepository
import ua.com.dekalo.hometask.models.DataModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val postsRepository: PostsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _data = MutableLiveData<DataModel>()
    val data: LiveData<DataModel> get() = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadData(allowCache: Boolean = true) {
        compositeDisposable.add(
            postsRepository.load(GenericRepository.createGenericSpec(allowCache = allowCache))
                .doOnSubscribe { _isLoading.postValue(true) }
                .subscribeOn(Schedulers.io())
                .doAfterTerminate { _isLoading.postValue(false) }
                .subscribe(
                    { _data.postValue(DataModel(it)) },
                    { TODO("Add error handling here") })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
