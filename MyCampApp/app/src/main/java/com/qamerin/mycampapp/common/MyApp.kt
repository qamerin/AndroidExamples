package com.qame.smartcamp.common

import android.app.Application

class MyApp: Application() {
    var campId: Long = 0L
    var prevPage: String = ""
    var regularGearDetailId: Long = 0L

    companion object {
        private var instance : MyApp? = null

        fun  getInstance(): MyApp {
            if (instance == null)
                instance = MyApp()

            return instance!!
        }
    }
}