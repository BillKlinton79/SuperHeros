package com.example.superheros.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {
        fun getRetrofitInstance(url: String): Retrofit {
            return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}