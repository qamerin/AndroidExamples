package com.example.realmnamelistapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.time.LocalDate


open class MyModel: RealmObject() {
    @PrimaryKey
    var id : Long=0
    var name: String = ""
    var age: Long = 0
    private var _date: Long = LocalDate.now().toEpochDay()
    var day: LocalDate
        get() { return LocalDate.ofEpochDay(_date) }
        set(value) { _date = value.toEpochDay() }

}