package ua.com.dekalo.hometask.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heershingenmosiken.assertions.Assertions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.com.dekalo.hometask.domain.CommentsRepository
import ua.com.dekalo.hometask.models.Comment
import ua.com.dekalo.hometask.models.Post
import javax.inject.Inject

sealed class DetailsItem
data class PostDetailsItem(val post: Post) : DetailsItem()
data class CommentDetailsItem(val comment: Comment) : DetailsItem()

open class DetailsActivityViewModel @Inject constructor(private val commentsRepository: CommentsRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _detailsContent = MutableLiveData<List<DetailsItem>>()
    val detailsContent: LiveData<List<DetailsItem>> get() = _detailsContent

    private var post: Post? = null
    private var comments = listOf<Comment>()

    fun init(post: Post) {
        this.post = post
        mergeContentAndNotify()

        compositeDisposable.add(
            commentsRepository
                .load(CommentsRepository.createCommentsSpec(post.id))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    comments = it
                    mergeContentAndNotify()
                }, {
                    Assertions.fail { IllegalStateException("TODO error handling", it) }
                })
        )
    }

    private fun mergeContentAndNotify() {
        val items = mutableListOf<DetailsItem>()
        post?.let { items.add(PostDetailsItem(it)) }
        items.addAll(comments.map { CommentDetailsItem(it) })
        _detailsContent.postValue(items)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}