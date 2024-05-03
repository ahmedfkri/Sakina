package com.example.sakina.core.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

class UIPrefrences {

    fun applyTheme(selectedTheme: String) {
        when (selectedTheme) {
            "Light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    fun changeLanguage(context: Context, language: String) {
        val locale = when (language) {
            "English" -> Locale.ENGLISH
            "Arabic" -> Locale("ar")
            else -> Locale.getDefault()
        }
        Locale.setDefault(locale)
        val configuration = Configuration().apply {
            setLocale(locale)
        }
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}