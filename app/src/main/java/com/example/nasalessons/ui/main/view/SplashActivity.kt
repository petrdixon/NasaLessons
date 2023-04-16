package com.example.nasalessons.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nasalessons.R
import com.example.nasalessons.databinding.ActivitySplashBinding
import com.example.nasalessons.ui.main.model.GetDate
import com.example.nasalessons.ui.main.viewmodel.MainViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 3000L
    private val rotateValue = 750f
    private val interpolatorDuration = 10000L
    val b = Bundle()

    // для отслеживания статуса загрузки первого экрана объявляю ViewModel
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.fly_01).into(binding.imageView)
        binding.imageView.animate().rotationBy(rotateValue)
            .setInterpolator(LinearInterpolator()).duration = interpolatorDuration


        // подписываюсь на изменения liveData, передаю нужную дату
        viewModel.getDataNasaFromViewModel(GetDate().getDate(2))
            .observe(this) {
                // Готовлю данные для передачи и передаю с помощью intent в MainActivity. Принимаю и использую сразу в Pictures3DaysFragment
                val data2: Parcelable = it as Parcelable
                b.putParcelable("data", data2)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java).putExtra("data", b))
                finish()
            }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }


}



