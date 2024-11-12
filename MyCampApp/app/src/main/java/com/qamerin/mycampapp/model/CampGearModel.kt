package com.qame.smartcamp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CampGearModel: RealmObject() {
    @PrimaryKey
    var campGearId : Long=0
    var campGearName: String = ""
    var qauantity: Long=0
    var gearCategoryId: Long=0
    var isCarLoaded : Boolean=false
    var campId : Long=0
    var dspSeq : Long = 0


    override fun toString(): String {
        return campGearName
    }
}