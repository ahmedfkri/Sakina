package com.example.sakina.core

import android.app.Application
import com.example.sakina.core.data.MySharedPref

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPref.init(this)
    }
}