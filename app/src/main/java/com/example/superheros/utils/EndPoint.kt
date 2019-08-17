package com.example.superheros.utils

import com.example.superheros.models.Hero
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface EndPoint {
    @GET("search/{search}")
    fun getHeros(@Path("search") search: String) : Call<Hero>
}