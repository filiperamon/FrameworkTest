package com.example.frameworktest.data.response

import com.google.gson.annotations.SerializedName

class AlbumsBodyResponse (
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)