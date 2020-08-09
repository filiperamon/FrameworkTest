package com.example.frameworktest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.frameworktest.data.model.Album
import com.example.frameworktest.ui.albums.AlbumViewParams

@Entity(tableName = "album")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true) val cod: Long = 0,
    val userId: Int,
    val id: Int,
    val title: String
)

fun AlbumViewParams.toAlbumEntity(): AlbumEntity {
    return with(this) {
        AlbumEntity(
            userId = this.userId,
            id = this.id,
            title = this.title
        )
    }
}

fun AlbumEntity.toAlbum(): Album {
    return Album(
        userId = this.userId,
        id = this.id,
        title = this.title
    )
}