package com.example.realmnamelistapp.common

import android.app.Application

class MyApp: Application() {
    var campId: Long = 0L

    companion object {
        private var instance : MyApp? = null

        fun  getInstance(): MyApp {
            if (instance == null)
                instance = MyApp()

            return instance!!
        }
    }
}