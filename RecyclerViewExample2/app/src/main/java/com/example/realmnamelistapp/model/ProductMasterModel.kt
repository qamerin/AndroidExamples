package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.time.LocalDate


open class ProductMasterModel: RealmObject() {
    @PrimaryKey
    var id : Long=0
    var productName: String = ""
    var brandName: String = ""
    var categoryId: Long=0
}