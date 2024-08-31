package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CategoryMasterModel: RealmObject() {
    @PrimaryKey
    var categoryId : Long=0
    var categoryName: String = ""

    override fun toString(): String {
        return categoryName
    }
}