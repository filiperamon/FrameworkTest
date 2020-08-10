package com.example.frameworktest.data.response

import com.example.frameworktest.data.model.Comments
import com.google.gson.annotations.SerializedName

class CommentsBodyResponse (
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("body")
    val body: String
){
    fun getCommentsModel() = Comments(
        postId = this.postId,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body
    )
}