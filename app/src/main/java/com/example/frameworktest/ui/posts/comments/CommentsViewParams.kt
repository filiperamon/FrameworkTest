package com.example.frameworktest.ui.posts.comments

data class CommentsViewParams(
    val postId: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val body: String = ""
)
