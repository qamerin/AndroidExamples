package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class GoodsMasterModel: RealmObject() {
    @PrimaryKey
    var id : Long=0
    var name: String = ""
    var categoryId: Long=0
}