package com.example.frameworktest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.frameworktest.data.model.Post
import com.example.frameworktest.ui.posts.PostViewParams

@Entity(tableName = "post")
data class PostEntity (
    @PrimaryKey(autoGenerate = true) val cod: Long = 0,
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostViewParams.toPostEntity(): PostEntity {
    return with(this){
        PostEntity(
            id = this.id,
            userId = this.userId,
            title = this.title,
            body = this.body
        )
    }
}

fun PostEntity.toPost(): Post {
    return Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}