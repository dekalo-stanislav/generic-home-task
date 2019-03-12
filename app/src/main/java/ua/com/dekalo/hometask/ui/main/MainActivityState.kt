package ua.com.dekalo.hometask.ui.main

import ua.com.dekalo.hometask.models.Post

data class MainActivityState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)