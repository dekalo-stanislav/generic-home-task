package ua.com.dekalo.hometask.domain

import io.reactivex.Observable
import ua.com.dekalo.hometask.models.Comment
import ua.com.dekalo.hometask.network.ServiceApi

class DefaultCommentsRepository(private val api: ServiceApi) : CommentsRepository {
    override fun load(commentsLoadingParams: CommentsLoadingParams): Observable<List<Comment>> {
        return api.getComments(commentsLoadingParams.postId).toObservable()
    }
}