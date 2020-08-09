package com.example.frameworktest.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frameworktest.data.model.Post
import com.example.frameworktest.data.repository.PostRepository
import com.example.frameworktest.data.response.PostsBodyResponse
import com.example.frameworktest.service.APIService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    val postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()

    fun getPosts() {
        viewModelScope.launch {
            val posts: List<Post> = getPostsDb()

            if(posts.isEmpty()){
                getPostsApi()
            } else {
                postsLiveData.value = posts
            }
        }
    }

    fun savePostDb() {
        viewModelScope.launch {
            for (result in postsLiveData.value!!) {
                val postsViewParams = PostViewParams(
                    userId = result.userId,
                    id = result.id,
                    title = result.title,
                    body = result.body
                )
                postRepository.createPost(postsViewParams)
            }
        }
    }

    private suspend fun getPostsDb(): List<Post>{
        return postRepository.getAllPost()
    }

    private fun getPostsApi() {
        APIService.service.getPosts().enqueue(object : Callback<List<PostsBodyResponse>> {
            override fun onResponse(
                call: Call<List<PostsBodyResponse>>,
                response: Response<List<PostsBodyResponse>>
            ) {
                if (response.isSuccessful) {
                    val posts: MutableList<Post> = mutableListOf()

                    response.body()?.let { PostsBodyResponse ->
                        for (result in PostsBodyResponse){
                            val post = Post(
                                userId = result.userId,
                                id = result.id,
                                title = result.title,
                                body = result.body
                            )
                            posts.add(post)
                        }
                    }

                    postsLiveData.value = posts
                    savePostDb()
                }
            }
            override fun onFailure(call: Call<List<PostsBodyResponse>>, t: Throwable) {

            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    class PostViewModelFactory(private val postRepository: PostRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsViewModel(postRepository) as T
        }
    }
}