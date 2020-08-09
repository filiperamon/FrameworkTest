package com.example.frameworktest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frameworktest.data.db.AlbumEntity

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun save(album: AlbumEntity)

    @Query("SELECT * FROM todo")
    suspend fun getAllAlbum(): List<AlbumEntity>
}