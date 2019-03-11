package ua.com.dekalo.hometask.domain

import io.reactivex.Observable
import ua.com.dekalo.hometask.models.Comment

data class CommentsLoadingParams(val postId: Long, val allowCache: Boolean = true, val loadFresh: Boolean = true)

interface CommentsRepository {

    companion object {
        fun createLoadingParams(
            postId: Long,
            allowCache: Boolean = true,
            loadFresh: Boolean = true
        ): CommentsLoadingParams {
            return CommentsLoadingParams(postId, allowCache, loadFresh)
        }
    }

    fun load(commentsLoadingParams: CommentsLoadingParams): Observable<List<Comment>>
}