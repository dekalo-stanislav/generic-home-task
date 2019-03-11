package ua.com.dekalo.hometask.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ua.com.dekalo.hometask.models.Comment
import ua.com.dekalo.hometask.models.Post

interface ServiceApi {

    @GET("posts")
    fun getPosts(): Single<List<Post>>

    @GET("comments/{postId}/comments")
    fun getComments(@Path("postId") postId: Long): Single<List<Comment>>
}