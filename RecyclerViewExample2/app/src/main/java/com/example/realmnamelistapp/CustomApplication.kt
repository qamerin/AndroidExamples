package com.example.realmnamelistapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
//            .assetFile("initial_data.realm")
            .build()
        Realm.setDefaultConfiguration(config)

    }

}