package com.example.myapplication.Retrofit
import com.example.myapplication.SaveSharedPreferences
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    var BASE_URL:String="http://"
    fun getClient(url:String): Retrofit {
        BASE_URL+=url
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}