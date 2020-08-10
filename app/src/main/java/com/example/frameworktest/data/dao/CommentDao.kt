package com.example.frameworktest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frameworktest.data.db.CommentsEntity
import com.example.frameworktest.data.model.Comments
import com.example.frameworktest.ui.posts.comments.CommentsViewParams

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun save(comments: CommentsEntity)

    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getCommentById(postId:Int): List<CommentsEntity>
}