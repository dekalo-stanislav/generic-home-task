package ua.com.dekalo.hometask.domain

import io.reactivex.Observable
import ua.com.dekalo.hometask.models.Post

data class LoadingStrategy(val allowCache: Boolean, val loadFresh: Boolean)

interface PostsRepository {

    companion object {
        fun createLoadingStrategy(allowCache: Boolean = true, loadFresh: Boolean = true): LoadingStrategy {
            return LoadingStrategy(allowCache, loadFresh)
        }
    }

    fun loadPosts(loadingStrategy: LoadingStrategy = createLoadingStrategy()): Observable<List<Post>>
}