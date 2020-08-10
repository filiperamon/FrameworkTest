package com.example.frameworktest.data.repository.post.comments

import com.example.frameworktest.data.model.Comments
import com.example.frameworktest.ui.posts.comments.CommentsViewParams

interface CommentsRepository {
    suspend fun createPost(commentsViewParams: CommentsViewParams)
    suspend fun getCommentByPostId(postId:Int): List<Comments>
}