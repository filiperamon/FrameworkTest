package com.example.frameworktest.data.response

import com.google.gson.annotations.SerializedName

class TodosBodyResponse (
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("completed")
    val completed: Boolean
)