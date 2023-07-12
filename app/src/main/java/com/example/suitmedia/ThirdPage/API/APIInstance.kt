package com.example.suitmedia.ThirdPage.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun runApiService():ApiService {
        return retrofit.create(ApiService::class.java)
    }
}