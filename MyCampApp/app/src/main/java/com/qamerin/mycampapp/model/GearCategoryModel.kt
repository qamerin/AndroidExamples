package com.qamerin.mycampapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class GearCategoryModel: RealmObject() {
    @PrimaryKey
    var gearCategoryId : Long=0
    var categoryName: String = ""
}