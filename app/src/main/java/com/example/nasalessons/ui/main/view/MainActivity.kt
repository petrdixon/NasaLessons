package com.example.nasalessons.ui.main.view

import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasalessons.R
import com.example.nasalessons.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
            setTheme(R.style.DayRedTheme)
        }
        val getPrefLightTheme = getPreferences(Context.MODE_PRIVATE).getBoolean("LightTheme", true)
        val getPrefNightTheme = getPreferences(Context.MODE_PRIVATE).getBoolean("DarkTheme", false)
        if (getPrefLightTheme) setTheme(R.style.DayRedTheme)
        if (getPrefNightTheme) setTheme(R.style.NightRedTheme)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pictures_3_days -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment())
                        .commit()

                    true
                }
                R.id.pictures_any_day -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureDatePickerFragment())
                        .commit()
                    true
                }
                R.id.mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }


        // TODO временно скрыл нижние кнопки
//        binding.bottomNavigationView.visibility = View.GONE

    }
}


