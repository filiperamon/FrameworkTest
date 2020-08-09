package com.example.frameworktest.data.repository

import com.example.frameworktest.data.model.Post
import com.example.frameworktest.ui.posts.PostViewParams

interface PostRepository {
    suspend fun createPost(postViewParams: PostViewParams)
    suspend fun getAllPost(): List<Post>
}