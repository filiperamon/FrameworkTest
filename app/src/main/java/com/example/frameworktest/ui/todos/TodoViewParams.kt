package com.example.frameworktest.ui.todos

data class TodoViewParams(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)