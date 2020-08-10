package com.example.frameworktest.data.repository.post.comments

import com.example.frameworktest.data.dao.CommentDao
import com.example.frameworktest.data.db.CommentsEntity
import com.example.frameworktest.data.db.toComments
import com.example.frameworktest.data.db.toCommentsEntity
import com.example.frameworktest.data.model.Comments
import com.example.frameworktest.ui.posts.comments.CommentsViewParams

class CommentsDbDataSource(
    private val commentDao: CommentDao
) : CommentsRepository {
    override suspend fun createPost(commentsViewParams: CommentsViewParams) {
        val commentsEntity: CommentsEntity = commentsViewParams.toCommentsEntity()
        commentDao.save(commentsEntity)
    }

    override suspend fun getCommentByPostId(postId: Int): List<Comments> {
        val listCommentsEntity:List<CommentsEntity> = commentDao.getCommentById(postId)
        val listComments: MutableList<Comments> = mutableListOf()

        for(result in listCommentsEntity){
            val comments:Comments = result.toComments()
            listComments.add(comments)
        }

        return listComments
    }
}