package com.example.frameworktest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.frameworktest.data.model.Todo
import com.example.frameworktest.ui.todos.TodoViewParams

@Entity(tableName = "todo")
data class TodoEntity (
    @PrimaryKey(autoGenerate = true) val cod: Long = 0,
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

fun TodoViewParams.toTodoEntity(): TodoEntity {
    return with(this){
        TodoEntity(
            userId = this.userId,
            id = this.id,
            title = this.title,
            completed = this.completed
        )
    }
}

fun TodoEntity.toTodo(): Todo {
    return Todo(
        userId = this.userId,
        id = this.id,
        title = this.title,
        completed = this.completed
    )
}