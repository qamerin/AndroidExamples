package com.example.realmnamelistapp.goods

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class GoodsModel: RealmObject() {
    @PrimaryKey
    var id : Long=0
    var campId : Long=0
    var name: String = ""
    var category: String = ""
}