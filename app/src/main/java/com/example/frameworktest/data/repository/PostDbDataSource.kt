package com.example.frameworktest.data.repository

import com.example.frameworktest.data.dao.PostDao
import com.example.frameworktest.data.db.PostEntity
import com.example.frameworktest.data.db.toPost
import com.example.frameworktest.data.db.toPostEntity
import com.example.frameworktest.data.model.Post
import com.example.frameworktest.ui.posts.PostViewParams

class PostDbDataSource(
    private val postDao: PostDao
) : PostRepository{

    override suspend fun createPost(postViewParams: PostViewParams) {
        val postEntity: PostEntity = postViewParams.toPostEntity()
        postDao.save(postEntity)
    }

    override suspend fun getAllPost(): List<Post> {
        val listPostEntity:List<PostEntity> = postDao.getAllPost()
        val listPost: MutableList<Post> = mutableListOf()

        for (result in listPostEntity){
            val post:Post = result.toPost()
            listPost.add(post)
        }

        return listPost
    }
}