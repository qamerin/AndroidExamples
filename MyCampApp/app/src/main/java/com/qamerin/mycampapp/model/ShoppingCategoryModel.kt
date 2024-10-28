package com.qamerin.mycampapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class ShoppingCategoryModel: RealmObject() {
    @PrimaryKey
    var shoppingCategoryId : Long=0
    var categoryName: String = ""
}