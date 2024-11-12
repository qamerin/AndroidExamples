package com.qame.smartcamp.import

import com.qame.smartcamp.common.CustomApplication
import com.qame.smartcamp.model.CampGearDefaultModel
import com.qame.smartcamp.model.CampgroundMasterModel
import com.qame.smartcamp.model.GearCategoryModel
import com.qame.smartcamp.model.ShoppingCategoryModel
import io.realm.Realm

class MyTransaction : Realm.Transaction {
    override fun execute(realm: Realm?) {
        realm?.let {
            CustomApplication.instance?.let {
                source ->
                it.createAllFromJson(CampgroundMasterModel::class.java, source.resources.assets.open("campgrounds.json"))
                it.createAllFromJson(CampGearDefaultModel::class.java, source.resources.assets.open("camp_gear_default_data.json"))
                it.createAllFromJson(GearCategoryModel::class.java, source.resources.assets.open("category_default_data.json"))
                it.createAllFromJson(ShoppingCategoryModel::class.java, source.resources.assets.open("shopping_category_default_data.json"))
            }
        }
    }
}
