package com.qame.smartcamp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ShoppingItemModel: RealmObject() {
    @PrimaryKey
    var shoppingItemId : Long = 0
    var shoppingItemName: String = ""
    var shoppingCategoryId: Long=0
    var isItemBought : Boolean = false
    var campId : Long = 0
    var dspSeq : Long = 0
}