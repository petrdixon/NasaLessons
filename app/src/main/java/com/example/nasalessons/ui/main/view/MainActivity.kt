package com.example.nasalessons.ui.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nasalessons.R
import com.example.nasalessons.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_fragment.*


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
                R.id.bottom_view_earth -> {
                    Toast.makeText(this, "Earth", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment())
                        .commit()

                    true
                }
                R.id.bottom_view_mars -> {
                    Toast.makeText(this, "Mars", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_view_weather -> {
                    Toast.makeText(this, "Weather", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // TODO временно скрыл нижние кнопки
//        binding.bottomNavigationView.visibility = View.GONE

    }
}


