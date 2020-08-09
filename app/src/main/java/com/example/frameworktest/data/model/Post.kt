package com.example.frameworktest.data.model

import com.google.gson.annotations.SerializedName

data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)