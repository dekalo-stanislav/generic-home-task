package ua.com.dekalo.hometask.domain

import com.heershingenmosiken.assertions.Assertions
import io.reactivex.Observable
import ua.com.dekalo.hometask.cache.PostsCache
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.network.ServiceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPostsRepository @Inject constructor(private val api: ServiceApi, private val postsCache: PostsCache) :
    PostsRepository {

    override fun load(spec: GenericSpec): Observable<List<Post>> {

        Assertions.assertTrue(spec.loadFresh || spec.allowCache) { IllegalStateException("PostsLoadingParams is empty") }

        var result: Observable<List<Post>> = Observable.empty()

        if (!postsCache.isEmpty() && spec.allowCache) {
            postsCache.get()?.let {
                result = result.concatWith(Observable.just(it))
            }
        }

        if (spec.loadFresh) {
            result = result.concatWith(api.getPosts()
                .doOnSuccess { postsCache.put(it) })
        }

        return result
    }

}