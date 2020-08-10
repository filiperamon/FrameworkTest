package com.example.frameworktest.ui.posts.comments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frameworktest.data.model.Comments
import com.example.frameworktest.data.repository.post.comments.CommentsRepository
import com.example.frameworktest.data.response.CommentsBodyResponse
import com.example.frameworktest.service.APIService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsViewModel(
    private val commentsRepository: CommentsRepository
) : ViewModel() {

    val commentsLiveData: MutableLiveData<List<Comments>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getComments(postId:Int){
        viewModelScope.launch {
            val listComments: List<Comments> = getCommentsDb(postId)

            if(listComments.isEmpty()){
                getCommentsAPI(postId)
            } else {
                commentsLiveData.value = listComments
                viewFlipperLiveData.value = VIEW_FLIPPER_COMMENTS
            }
        }
    }

    private suspend fun getCommentsDb(postId:Int): List<Comments>{
        return commentsRepository.getCommentByPostId(postId)
    }

    fun saveCommandsDb() {
        viewModelScope.launch {
            for (result in commentsLiveData.value!!) {
                val commandsViewParams = CommentsViewParams(
                 postId = result.postId,
                 id = result.id,
                 name = result.name,
                 email  = result.email,
                 body  = result.body
                )
                commentsRepository.createPost(commandsViewParams)
            }
        }
    }

    private fun getCommentsAPI(postId:Int) {
        APIService.service.getComments(postId).enqueue(object : Callback<List<CommentsBodyResponse>>{
            override fun onResponse(
                call: Call<List<CommentsBodyResponse>>,
                response: Response<List<CommentsBodyResponse>>
            ) {
                if(response.isSuccessful){
                    val listComments: MutableList<Comments> = mutableListOf()

                    response.body()?.let { commentsBodyResponse ->
                        for(result in commentsBodyResponse){
                            val comments:Comments = result.getCommentsModel()
                            listComments.add(comments)
                        }
                    }

                    commentsLiveData.value = listComments
                    viewFlipperLiveData.value = VIEW_FLIPPER_COMMENTS
                    saveCommandsDb()
                }
            }

            override fun onFailure(call: Call<List<CommentsBodyResponse>>, t: Throwable) {
                Log.d("FRSB", "FATAL ERROR")
            }
        })

    }

    companion object {
        private const val VIEW_FLIPPER_COMMENTS = 1
    }

    @Suppress("UNCHECKED_CAST")
    class CommandsViewModelFactory(private val commandRepository: CommentsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CommentsViewModel(commandRepository) as T
        }

    }
}