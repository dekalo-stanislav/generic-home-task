package ua.com.dekalo.hometask.domain

import io.reactivex.Observable
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.network.PostsApi

class DefaultPostsRepository(private val api: PostsApi) : PostsRepository {

    override fun loadPosts(loadingStrategy: LoadingStrategy): Observable<List<Post>> {
        return api.getPosts().toObservable()
    }

}