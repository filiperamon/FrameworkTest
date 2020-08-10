package com.example.frameworktest.data.response

import com.example.frameworktest.data.model.Album
import com.google.gson.annotations.SerializedName

class AlbumsBodyResponse (
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
){
    fun getAlbumModel() = Album(
        userId = this.userId,
        id = this.id,
        title = this.title
    )
}