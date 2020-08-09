package com.example.frameworktest.ui.posts

data class PostViewParams (
    var id: Int = 0,
    var title: String = "",
    val userId: Int = 0,
    val body: String = ""
)