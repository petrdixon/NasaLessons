package com.example.nasalessons.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nasalessons.R
import com.example.nasalessons.databinding.ActivityBottomNavigationBinding


class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    Toast.makeText(this, "Earth", Toast.LENGTH_SHORT).show()
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
    }
}