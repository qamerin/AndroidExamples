package com.example.simplelistapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication: Application() {

    companion object {
        var instance: CustomApplication? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)

        val config = RealmConfiguration.Builder().name("data.realm")
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)

    }

}