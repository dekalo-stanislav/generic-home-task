package ua.com.dekalo.hometask.cache

import ua.com.dekalo.hometask.models.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsCache @Inject constructor() : InMemoryCache<List<Post>>()