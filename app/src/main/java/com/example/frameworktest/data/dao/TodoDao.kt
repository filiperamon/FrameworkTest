package com.example.frameworktest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frameworktest.data.db.TodoEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun save(todo: TodoEntity)

    @Query("SELECT * FROM todo")
    suspend fun getAllTodos(): List<TodoEntity>
}