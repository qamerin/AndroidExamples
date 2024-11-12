package com.qame.smartcamp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CampgroundMasterModel: RealmObject() {
    @PrimaryKey
    var campgroundId : Long=0
    var campgroundName: String = ""
    var address: String = ""
    var lattitude: Double=0.0
    var longitude: Double=0.0
    var dspSeq : Long = 0
    var prefectureId : Long = 0
}