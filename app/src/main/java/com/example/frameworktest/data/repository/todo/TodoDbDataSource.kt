package com.example.frameworktest.data.repository.todo

import com.example.frameworktest.data.dao.TodoDao
import com.example.frameworktest.data.db.TodoEntity
import com.example.frameworktest.data.db.toTodo
import com.example.frameworktest.data.db.toTodoEntity
import com.example.frameworktest.data.model.Todo
import com.example.frameworktest.ui.todos.TodoViewParams

class TodoDbDataSource(
    private val todoDao: TodoDao
) : TodoRepository {

    override suspend fun createTodo(todoViewParams: TodoViewParams) {
        val todoEntity: TodoEntity = todoViewParams.toTodoEntity()
        todoDao.save(todoEntity)
    }

    override suspend fun getAllTodo(): List<Todo> {
        val listTodoEntity:List<TodoEntity> = todoDao.getAllTodos()
        val listTodo: MutableList<Todo> = mutableListOf()

        for (result in listTodoEntity){
            val todo:Todo = result.toTodo()
            listTodo.add(todo)
        }

        return listTodo
    }
}