package ua.com.dekalo.hometask.models

import java.io.Serializable

data class Comment(
    val id: Long,
    val postId: Long,
    val content: String,
    val author: String
) : Serializable