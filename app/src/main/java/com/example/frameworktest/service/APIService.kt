package com.example.frameworktest.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    private fun initRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: FrameService = initRetrofit().create(FrameService::class.java)
}