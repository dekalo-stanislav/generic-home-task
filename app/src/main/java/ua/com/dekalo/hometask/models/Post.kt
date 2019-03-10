package ua.com.dekalo.hometask.models

import java.io.Serializable

data class Post(
    val id: Long,
    val title: String
) : Serializable