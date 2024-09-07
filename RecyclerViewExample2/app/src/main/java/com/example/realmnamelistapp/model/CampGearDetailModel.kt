package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CampGearDetailModel: RealmObject() {
    @PrimaryKey
    var campGearDetailId : Long=0
    var campGearId: Long=0
    var campGearName : String=""
    var goodsId: Long=0
    var campId : Long=0
}