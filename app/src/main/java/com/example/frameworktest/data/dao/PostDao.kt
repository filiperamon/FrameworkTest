package com.example.frameworktest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frameworktest.data.db.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun save(post: PostEntity)

    @Query("SELECT * FROM post")
    suspend fun getAllPost(): List<PostEntity>
}