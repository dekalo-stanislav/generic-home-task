package ua.com.dekalo.hometask.domain

import io.reactivex.Observable
import ua.com.dekalo.hometask.models.Post

data class PostsLoadingParams(val allowCache: Boolean, val loadFresh: Boolean)

interface PostsRepository {

    companion object {
        fun createLoadingParams(allowCache: Boolean = true, loadFresh: Boolean = true): PostsLoadingParams {
            return PostsLoadingParams(allowCache, loadFresh)
        }
    }

    fun loadPosts(loadingStrategy: PostsLoadingParams = createLoadingParams()): Observable<List<Post>>
}