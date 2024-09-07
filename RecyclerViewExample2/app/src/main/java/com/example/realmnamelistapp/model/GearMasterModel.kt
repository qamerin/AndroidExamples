package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class GearMasterModel: RealmObject() {
    @PrimaryKey
    var gearMasterId : Long=0
    var gearName: String = ""
    var brandName: String = ""
    var campGearId: Long=0
}