package com.example.nasalessons.ui.main.model

import com.example.nasalessons.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNasa {
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/") // часть url, которая останется неизменной при любом запросе
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .client(OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) // логгирование. У меня не заработало
            ).build()
        )
        .build().create(RetrofitInterface::class.java) // указываем интерфейс

    fun getDataNasa(callback: Callback<ModelRetrofitNasa>) {
        val apiKey: String = BuildConfig.NASA_API_KEY // получили ключ из properties
        retrofitBuilder.getData(apiKey).enqueue(callback) // retrofit сам обращается к функции getTop250() в интерфейсе
    }
}