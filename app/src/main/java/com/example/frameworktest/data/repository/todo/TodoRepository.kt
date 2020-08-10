package com.example.frameworktest.data.repository.todo

import com.example.frameworktest.data.model.Todo
import com.example.frameworktest.ui.todos.TodoViewParams

interface TodoRepository {
    suspend fun createTodo(todoViewParams: TodoViewParams)
    suspend fun getAllTodo(): List<Todo>
}