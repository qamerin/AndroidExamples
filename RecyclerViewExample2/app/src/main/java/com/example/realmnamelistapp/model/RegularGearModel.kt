package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class RegularGearModel: RealmObject() {
    @PrimaryKey
    var regularGearId : Long=0
    var gearName: String = ""

    override fun toString(): String {
        return gearName
    }
}