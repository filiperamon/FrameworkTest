package com.example.frameworktest.ui.todos

import android.telecom.Connection
import android.util.Log
import androidx.lifecycle.*
import com.example.frameworktest.data.model.Todo
import com.example.frameworktest.data.repository.TodoRepository
import com.example.frameworktest.data.response.TodosBodyResponse
import com.example.frameworktest.service.APIService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodosViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todoLiveData: MutableLiveData<List<Todo>> = MutableLiveData()

    fun getTodos() {
        viewModelScope.launch {
            val todos: List<Todo> = getTodosDb()

            if(todos.isEmpty()){
                getTodosApi()
            } else {
                todoLiveData.value = todos
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
                if (response.isSuccessful) {
                    val todos: MutableList<Todo> = mutableListOf()

                    response.body()?.let { TodosBodyResponse ->
                        Log.d("FRSB", "TodosViewModel")
                        for (result in TodosBodyResponse) {
                            val todo: Todo = Todo(
                                userId = result.userId,
                                id = result.id,
                                title = result.title,
                                completed = result.completed
                            )
                            todos.add(todo)
                        }
                    }

                    todoLiveData.value = todos
                    saveTodosDb()
                }
            }

            override fun onFailure(call: Call<List<TodosBodyResponse>>, t: Throwable) {
            }
        })
    }

    class TodoViewModelFactory(private val todoRepository: TodoRepository) :
        ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TodosViewModel(todoRepository) as T
        }
    }
}