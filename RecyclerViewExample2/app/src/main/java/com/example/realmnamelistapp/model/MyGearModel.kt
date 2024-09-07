package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


//open class GoodsMasterModel: RealmObject() {
open class MyGearModel: RealmObject() {
    @PrimaryKey
    var myGearId : Long=0
    var gearName: String = ""
    var campGearId: Long=0
    var isSelected = false

    override fun toString(): String {
        return gearName
    }


}