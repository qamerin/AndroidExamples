package com.qamerin.mycampapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CampGearDefaultModel: RealmObject() {
    @PrimaryKey
    var defaultCampGearId : Long=0
    var campGearName: String = ""
    var categoryName: String = ""
    var gearCategoryId: Long=0
}