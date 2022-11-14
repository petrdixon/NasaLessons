package com.example.nasalessons.ui.main.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitInterface {
    //    @GET("planetary/apod")
    @GET("planetary/apod")
    fun getData(
//        @Header("X-Yandex-API-Key") token: String, // значения для отправки хэдера в запросе
        @Query("api_key") nasaApiKey: String, // сюда передаем ключ как параметр getData(..параметр..)
        @Query("date") needDate: String,
    ): Call<ModelRetrofitNasa>
}