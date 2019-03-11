package ua.com.dekalo.hometask.domain

import com.heershingenmosiken.assertions.Assertions
import io.reactivex.Observable
import ua.com.dekalo.hometask.cache.CommentsCache
import ua.com.dekalo.hometask.models.Comment
import ua.com.dekalo.hometask.network.ServiceApi
import java.util.concurrent.TimeUnit

class DefaultCommentsRepository(
    private val api: ServiceApi,
    private val commentsCache: CommentsCache
) : CommentsRepository {
    override fun load(spec: CommentsSpec): Observable<List<Comment>> {
        Assertions.assertTrue(spec.loadFresh || spec.allowCache) { IllegalStateException("CommentsLoadingParams is empty") }

        var result: Observable<List<Comment>> = Observable.empty()

        if (!commentsCache.isEmpty(spec.postId) && spec.allowCache) {
            commentsCache.get(spec.postId)?.let {
                result = result.concatWith(Observable.just(it))
            }
        }

        if (spec.loadFresh) {

            val networkObservable = api.getComments(spec.postId)
                .doOnSuccess { commentsCache.put(spec.postId, it) }

            result = result.concatWith(networkObservable)
        }

        return result
    }
}