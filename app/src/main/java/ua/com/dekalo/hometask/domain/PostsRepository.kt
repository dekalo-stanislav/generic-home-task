package ua.com.dekalo.hometask.domain

import ua.com.dekalo.hometask.models.Post

interface PostsRepository: GenericRepository<List<Post>, GenericSpec>