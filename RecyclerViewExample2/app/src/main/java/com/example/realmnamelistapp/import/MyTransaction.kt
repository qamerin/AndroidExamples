package com.example.realmnamelistapp.import

import com.example.realmnamelistapp.common.CustomApplication
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.GearMasterModel
import com.example.realmnamelistapp.model.RegularGearModel
import io.realm.Realm

class MyTransaction : Realm.Transaction {
    override fun execute(realm: Realm?) {
        realm?.let {
            CustomApplication.instance?.let {
                source ->
                it.createAllFromJson(GearMasterModel::class.java, source.resources.assets.open("gear_master_data.json"))
                it.createAllFromJson(RegularGearModel::class.java, source.resources.assets.open("regular_gear_data.json"))
            }
        }
    }
}
