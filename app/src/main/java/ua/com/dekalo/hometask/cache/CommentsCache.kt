package ua.com.dekalo.hometask.cache

import ua.com.dekalo.hometask.models.Comment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsCache @Inject constructor() : InMemoryKeyBasedCache<Long, List<Comment>>()