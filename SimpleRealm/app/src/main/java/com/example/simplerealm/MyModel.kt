package com.example.simplerealm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MyModel:RealmObject() {
    @PrimaryKey
    var id : Long = 0
    var name : String =""
}