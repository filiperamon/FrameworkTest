package com.example.frameworktest.data.response

import com.example.frameworktest.data.model.Post
import com.google.gson.annotations.SerializedName

class PostsBodyResponse (
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
){
    fun getPostModel() = Post(
        userId = this.userId,
        id = this.id,
        title = this.title,
        body = this.body
    )
}