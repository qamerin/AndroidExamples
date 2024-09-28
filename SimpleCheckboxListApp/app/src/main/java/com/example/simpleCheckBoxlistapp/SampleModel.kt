package com.example.simpleCheckBoxlistapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class SampleModel: RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var itemName: String = ""
}