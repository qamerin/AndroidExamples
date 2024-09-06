package com.example.realmnamelistapp.common

import android.app.Application
import com.example.realmnamelistapp.import.MyTransaction
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
            .initialData(MyTransaction())
//            .migration(MyMigration()) // fro data migration
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)

    }

}