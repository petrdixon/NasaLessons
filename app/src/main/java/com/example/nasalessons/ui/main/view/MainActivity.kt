package com.example.nasalessons.ui.main.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import com.example.nasalessons.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
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

    }
}


