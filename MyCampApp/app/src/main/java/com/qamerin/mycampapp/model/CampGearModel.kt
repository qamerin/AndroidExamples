package com.qamerin.mycampapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CampGearModel: RealmObject() {
    @PrimaryKey
    var campGearId : Long=0
    var campGearName: String = ""
    var campId : Long=0

    override fun toString(): String {
        return campGearName
    }
}