package com.example.nasalessons.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasalessons.ui.main.model.ModelRetrofitNasa
import com.example.nasalessons.ui.main.model.RetrofitNasa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()) : ViewModel() {
    fun getDataNasaFromViewModel(): LiveData<Any> {
        RetrofitNasa().getDataNasa(
            object : Callback<ModelRetrofitNasa> {
                override fun onFailure(call: Call<ModelRetrofitNasa>, t: Throwable) { // если ошибка
                    println("*** in MainViewModel onFailure $t")
                }
                // если ошибок нет, ответ получен
                override fun onResponse(call: Call<ModelRetrofitNasa>, response: Response<ModelRetrofitNasa>) {
                    val listData = response.body()
                    liveDataToObserve.value = listData
                }
            })
        return liveDataToObserve
    }
}

