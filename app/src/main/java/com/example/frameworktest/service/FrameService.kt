package com.example.frameworktest.service

import com.example.frameworktest.data.response.AlbumsBodyResponse
import com.example.frameworktest.data.response.CommentsBodyResponse
import com.example.frameworktest.data.response.PostsBodyResponse
import com.example.frameworktest.data.response.TodosBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FrameService {
    @GET("posts")
    fun getPosts(): Call<List<PostsBodyResponse>>

    @GET("posts/{postId}/comments")
    fun getComments(@Path("postId") postId:Int): Call<List<CommentsBodyResponse>>

    @GET("albums")
    fun getAlbums(): Call<List<AlbumsBodyResponse>>

    @GET("todos")
    fun getTodos(): Call<List<TodosBodyResponse>>
}