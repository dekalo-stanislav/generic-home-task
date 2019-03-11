package ua.com.dekalo.hometask.domain

import ua.com.dekalo.hometask.models.Comment

class CommentsSpec(val postId: Long, allowCache: Boolean = true, loadFresh: Boolean = true) :
    GenericSpec(allowCache, loadFresh)

interface CommentsRepository : GenericRepository<List<Comment>, CommentsSpec> {

    companion object {
        fun createCommentsSpec(
            postId: Long,
            allowCache: Boolean = true,
            loadFresh: Boolean = true
        ): CommentsSpec {
            return CommentsSpec(postId, allowCache, loadFresh)
        }
    }
}