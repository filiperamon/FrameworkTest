package com.example.frameworktest.ui.todos

import androidx.lifecycle.*
import com.example.frameworktest.data.model.Todo
import com.example.frameworktest.data.repository.TodoRepository
import com.example.frameworktest.data.response.TodosBodyResponse
import com.example.frameworktest.service.APIService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class TodosViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todoLiveData: MutableLiveData<List<Todo>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getTodos() {
        viewModelScope.launch {
            val todos: List<Todo> = getTodosDb()

            if(todos.isEmpty()){
                getTodosApi()
            } else {
                todoLiveData.value = todos
                viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POSTS, null)
            }
        }
    }

    fun saveTodosDb() {
        viewModelScope.launch {
            for (result in todoLiveData.value!!) {
                val todoViewParams = TodoViewParams(
                    userId = result.userId,
                    id = result.id,
                    title = result.title,
                    completed = result.completed
                )
                todoRepository.createTodo(todoViewParams)
            }
        }
    }

    private suspend fun getTodosDb(): List<Todo> {
        return todoRepository.getAllTodo()
    }

    private fun getTodosApi() {
        APIService.service.getTodos().enqueue(object : Callback<List<TodosBodyResponse>> {
            override fun onResponse(
                call: Call<List<TodosBodyResponse>>,
                response: Response<List<TodosBodyResponse>>
            ) {
                when {
                    response.isSuccessful -> {
                        val todos: MutableList<Todo> = mutableListOf()

                        response.body()?.let { TodosBodyResponse ->

                            for (result in TodosBodyResponse) {
                                val todo: Todo = result.getTodosModel()
                                todos.add(todo)
                            }
                        }

                        todoLiveData.value = todos
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POSTS, null)
                        saveTodosDb()

                    }
                    response.code() === 401 -> {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, com.example.frameworktest.R.string.error_401)
                    }
                    else -> {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, com.example.frameworktest.R.string.error_401_generic)
                    }
                }
            }

            override fun onFailure(call: Call<List<TodosBodyResponse>>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val VIEW_FLIPPER_POSTS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    @Suppress("UNCHECKED_CAST")
    class TodoViewModelFactory(private val todoRepository: TodoRepository) :
        ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TodosViewModel(todoRepository) as T
        }
    }
}