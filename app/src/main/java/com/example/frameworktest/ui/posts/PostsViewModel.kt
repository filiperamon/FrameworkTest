package com.example.frameworktest.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frameworktest.R
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
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getPosts() {
        viewModelScope.launch {
            val posts: List<Post> = getPostsDb()

            if(posts.isEmpty()){
                getPostsApi()
            } else {
                postsLiveData.value = posts
                viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POSTS, null)
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
                when {
                    response.isSuccessful -> {
                        val posts: MutableList<Post> = mutableListOf()

                        response.body()?.let { PostsBodyResponse ->
                            for (result in PostsBodyResponse){
                                val post = result.getPostModel()
                                posts.add(post)
                            }
                        }

                        postsLiveData.value = posts
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POSTS, null)
                        savePostDb()
                    }
                    response.code() === 401 -> {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_401)
                    }
                    else -> {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_401_generic)
                    }
                }
            }
            override fun onFailure(call: Call<List<PostsBodyResponse>>, t: Throwable) {
                viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_500)
            }
        })
    }

    companion object {
        private const val VIEW_FLIPPER_POSTS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    @Suppress("UNCHECKED_CAST")
    class PostViewModelFactory(private val postRepository: PostRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsViewModel(postRepository) as T
        }
    }
}