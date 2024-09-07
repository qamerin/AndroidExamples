package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class RegularGearDetailModel: RealmObject() {
    @PrimaryKey
    var regularGearDetailId : Long=0
    var regularGearId: Long=0
    var gearName : String=""
    var brand : String=""
    var isRegular : Boolean=false
}