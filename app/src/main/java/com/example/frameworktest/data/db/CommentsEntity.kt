package com.example.frameworktest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.frameworktest.data.model.Comments
import com.example.frameworktest.ui.posts.comments.CommentsViewParams

@Entity(tableName = "comments")
data class CommentsEntity (
    @PrimaryKey(autoGenerate = true) val cod: Long = 0,
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

fun CommentsViewParams.toCommentsEntity(): CommentsEntity{
    return CommentsEntity(
        postId = this.postId,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body
    )
}

fun CommentsEntity.toComments(): Comments{
    return Comments(
        postId = this.postId,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body
    )
}