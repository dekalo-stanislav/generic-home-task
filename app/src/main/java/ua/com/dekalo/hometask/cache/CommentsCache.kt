package ua.com.dekalo.hometask.cache

import ua.com.dekalo.hometask.models.Comment

class CommentsCache : InMemoryKeyBasedCache<Long, List<Comment>>()