package ua.com.dekalo.hometask.domain

import io.reactivex.Observable
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.network.ServiceApi

class DefaultPostsRepository(private val api: ServiceApi) : PostsRepository {

    override fun loadPosts(loadingStrategy: PostsLoadingParams): Observable<List<Post>> {
        return api.getPosts().toObservable()
    }

}