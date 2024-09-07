package com.example.realmnamelistapp.import

import com.example.realmnamelistapp.common.CustomApplication
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.GearMasterModel
import io.realm.Realm

class MyTransaction : Realm.Transaction {
    override fun execute(realm: Realm?) {
        realm?.let {
            CustomApplication.instance?.let {
                source ->
                it.createAllFromJson(CampGearModel::class.java, source.resources.assets.open("camp_gear_data.json"))
                it.createAllFromJson(GearMasterModel::class.java, source.resources.assets.open("gear_master_data.json"))
            }
        }
    }
}
