package com.example.frameworktest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.frameworktest.data.dao.AlbumDao
import com.example.frameworktest.data.dao.CommentDao
import com.example.frameworktest.data.dao.PostDao
import com.example.frameworktest.data.dao.TodoDao

@Database(
    entities = [PostEntity::class
        , TodoEntity::class
        , AlbumEntity::class
        , CommentsEntity::class]
    , version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun todoDao(): TodoDao
    abstract fun albumDao(): AlbumDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}