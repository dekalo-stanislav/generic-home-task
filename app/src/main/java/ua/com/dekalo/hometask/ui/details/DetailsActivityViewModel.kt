package ua.com.dekalo.hometask.ui.details

import androidx.lifecycle.ViewModel
import com.heershingenmosiken.assertions.Assertions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.com.dekalo.hometask.domain.CommentsRepository
import ua.com.dekalo.hometask.models.Comment
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.ui.utils.NonnullLiveData
import ua.com.dekalo.hometask.ui.utils.NonnullMutableLiveData
import javax.inject.Inject

sealed class DetailsItem
data class PostDetailsItem(val post: Post) : DetailsItem()
data class CommentDetailsItem(val comment: Comment) : DetailsItem()

open class DetailsActivityViewModel @Inject constructor(private val commentsRepository: CommentsRepository) :
    ViewModel() {

    companion object {
        fun mergeContentToDetailsItem(post: Post?, comments: List<Comment> = emptyList()): List<DetailsItem> {
            val items = mutableListOf<DetailsItem>()

            post?.let { items.add(PostDetailsItem(it)) }
            items.addAll(comments.map { CommentDetailsItem(it) })

            return items
        }
    }

    private val compositeDisposable = CompositeDisposable()

    @Volatile
    private var _lastDetailsData = DetailsActivityData()
    private val _detailsData = NonnullMutableLiveData(_lastDetailsData)
    val detailsData: NonnullLiveData<DetailsActivityData> get() = _detailsData

    private var post: Post? = null
    private var comments = emptyList<Comment>()

    fun init(post: Post) {
        this.post = post
        changeViewModelState { it.copy(items = mergeContentToDetailsItem(post)) }
    }

    fun load(allowCache: Boolean = true) {
        post?.let { post ->
            compositeDisposable.add(
                commentsRepository
                    .load(CommentsRepository.createCommentsSpec(post.id, allowCache = allowCache))
                    .doOnSubscribe { changeViewModelState { it.copy(isLoading = true) } }
                    .subscribeOn(Schedulers.io())
                    .subscribe({ comments ->
                        this.comments = comments
                        changeViewModelState {
                            it.copy(items = mergeContentToDetailsItem(post, comments), error = null)
                        }
                    }, { throwable ->
                        changeViewModelState { it.copy(isLoading = false, error = throwable) }
                    }, {
                        changeViewModelState { it.copy(isLoading = false) }
                    })
            )
        } ?: Assertions.fail { java.lang.IllegalStateException("Post is null") }

    }

    private fun changeViewModelState(change: (DetailsActivityData) -> DetailsActivityData) {
        _lastDetailsData = change(_lastDetailsData)
        _detailsData.postValue(_lastDetailsData)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}