package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class GoodsMasterModel: RealmObject() {
    @PrimaryKey
    var goodsId : Long=0
    var name: String = ""
    var categoryId: Long=0

    override fun toString(): String {
        return name
    }
}