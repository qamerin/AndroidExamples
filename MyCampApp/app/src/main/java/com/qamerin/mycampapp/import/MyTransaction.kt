package com.qamerin.mycampapp.import

import com.qamerin.mycampapp.common.CustomApplication
import com.qamerin.mycampapp.model.CampGearDefaultModel
import com.qamerin.mycampapp.model.CampgroundMasterModel
import io.realm.Realm

class MyTransaction : Realm.Transaction {
    override fun execute(realm: Realm?) {
        realm?.let {
            CustomApplication.instance?.let {
                source ->
                it.createAllFromJson(CampgroundMasterModel::class.java, source.resources.assets.open("campgrounds.json"))
                it.createAllFromJson(CampGearDefaultModel::class.java, source.resources.assets.open("camp_gear_default_data.json"))
            }
        }
    }
}
