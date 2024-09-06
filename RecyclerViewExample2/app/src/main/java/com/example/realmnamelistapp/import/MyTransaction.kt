package com.example.realmnamelistapp.import

import com.example.realmnamelistapp.common.CustomApplication
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.ProductMasterModel
import io.realm.Realm

class MyTransaction : Realm.Transaction {
    override fun execute(realm: Realm?) {
        realm?.let {
            CustomApplication.instance?.let {
                source ->
                it.createAllFromJson(CategoryMasterModel::class.java, source.resources.assets.open("item_data.json"))
                it.createAllFromJson(ProductMasterModel::class.java, source.resources.assets.open("product_master_data.json"))
            }
        }
    }
}
