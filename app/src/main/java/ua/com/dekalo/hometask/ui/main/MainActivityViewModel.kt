package ua.com.dekalo.hometask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.schedulers.Schedulers
import ua.com.dekalo.hometask.domain.PostsRepository
import ua.com.dekalo.hometask.models.DataModel
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    // TODO injectionthrough constructor
    @Inject
    var postsRepository: PostsRepository = DaggerMainActivityComponent.builder().build().getPostsRepository()

    private val compositeDisposable = CompositeDisposable()

    private val _data = MutableLiveData<DataModel>()
    val data: LiveData<DataModel> get() = _data

    fun loadData() {
        compositeDisposable.add(
            postsRepository.loadPosts()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { _data.postValue(DataModel(it)) },
                    { TODO("Add error handling here") })
        )
        _data.postValue(DataModel(listOf()))
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
