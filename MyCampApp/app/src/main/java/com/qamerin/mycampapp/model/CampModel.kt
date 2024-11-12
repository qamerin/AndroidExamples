package com.qame.smartcamp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.time.LocalDate


open class CampModel: RealmObject() {
    @PrimaryKey
    var campId : Long=0
    var campName: String = ""
//    var age: Long = 0
    var zipcode: String = ""
    var address: String = ""
    private var _startDate: Long = LocalDate.now().toEpochDay()
    var startDate: LocalDate
        get() { return LocalDate.ofEpochDay(_startDate) }
        set(value) { _startDate = value.toEpochDay() }
    private var _endDate: Long = LocalDate.now().toEpochDay()
    var endDate: LocalDate
        get() { return LocalDate.ofEpochDay(_endDate) }
        set(value) { _endDate = value.toEpochDay() }

}