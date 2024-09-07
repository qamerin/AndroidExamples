package com.example.realmnamelistapp.camp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CampGearDetailModel
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.CampModel
import com.example.realmnamelistapp.model.RegularGearDetailModel
import com.example.realmnamelistapp.model.RegularGearModel
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.time.LocalDate

class CampEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var startDbDate:LocalDate = LocalDate.now()
    private var endDbDate:LocalDate = LocalDate.now()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camp_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // ツールバーに戻るボタンを設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val etCampName : TextView = findViewById(R.id.etCampName)
        val etAddress : TextView = findViewById(R.id.etAdress)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        val etStartDate : TextView = findViewById(R.id.etStartDate)
        val btnStartDateCal : Button = findViewById(R.id.startDateButton)
        val etEndDate : TextView = findViewById(R.id.etEndDate)
        val btnEndDateCal : Button = findViewById(R.id.endDateButton)
        btnStartDateCal.setOnClickListener {
            showDatePicker(etStartDate)
        }
        btnEndDateCal.setOnClickListener {
            showDatePicker(etEndDate)
        }

        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("campId",0L)
        if(getId>0){
            val campModelResult = realm.where<CampModel>()
                .equalTo("campId",getId).findFirst()
            etCampName.text = campModelResult?.campName.toString()
            etAddress.text = campModelResult?.address.toString()
            etStartDate.text = campModelResult?.startDate?.year.toString()+
                    "/"+  campModelResult?.startDate?.monthValue.toString() +
                    "/" + campModelResult?.startDate?.dayOfMonth.toString()
            startDbDate = campModelResult?.startDate!!

            etEndDate.text = campModelResult?.endDate?.year.toString()+
                    "/"+  campModelResult?.endDate?.monthValue.toString() +
                    "/" + campModelResult?.endDate?.dayOfMonth.toString()
            endDbDate = campModelResult?.endDate!!

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }
        btnSave.setOnClickListener {
            var campName:String = ""
            var address:String = ""
            if(!etCampName.text.isNullOrEmpty()){
                campName = etCampName.text.toString()
            }
            if(!etAddress.text.isNullOrEmpty()){
                address = etAddress.text.toString()
            }

            if(getId == 0L){
                realm.executeTransaction {
                    val currentId = realm.where<CampModel>().max("campId")
                    val nextCampId = (currentId?.toLong()?:0L) + 1L
                    val campModel = realm.createObject<CampModel>(nextCampId)
                    campModel.campName = campName
                    campModel.address = address
                    val listStartDate = etStartDate.text.toString().split("/")
                    campModel.startDate =LocalDate.parse(listStartDate[0] +"-"  +listStartDate[1].padStart(2,'0') + "-" +  listStartDate[2].padStart(2,'0'))
                    val listEndDate = etEndDate.text.toString().split("/")
                    campModel.endDate =LocalDate.parse(listEndDate[0] +"-"  +listEndDate[1].padStart(2,'0') + "-" +  listEndDate[2].padStart(2,'0'))

                    // set camp gear info
                    val regularGearModel = realm.where<RegularGearModel>()
                        .findAll()
                    regularGearModel.forEach {
                        val campGearCurrentId = realm.where<CampGearModel>().max("campGearId")
                        val campGearNextId = (campGearCurrentId?.toLong() ?: 0L) + 1L
                        val campGearModel = realm.createObject<CampGearModel>(campGearNextId)
                        campGearModel.campGearName = it.gearName
                        campGearModel.campId = nextCampId

                        val regularGearDetailModel = realm.where<RegularGearDetailModel>()
                            .equalTo("regularGearId", it.regularGearId)
                            .findAll()

                        regularGearDetailModel.forEach{ v->
                            val campGearDetailCurrentId = realm.where<CampGearDetailModel>().max("campGearDetailId")
                            val campGearDetailNextId = (campGearDetailCurrentId?.toLong() ?: 0L) + 1L
                            val campGearDetailModel = realm.createObject<CampGearDetailModel>(campGearDetailNextId)
                            campGearDetailModel.campGearId = campGearNextId
                            campGearDetailModel.campGearName = v.gearName
                            campGearDetailModel.campId = nextCampId
                        }
                    }
                }
            }else{
                realm.executeTransaction{
                    val campModel = realm.where<CampModel>()
                        .equalTo("campId",getId).findFirst()
                    campModel?.campName = campName
                    campModel?.address = address
                    val listStartDate = etStartDate.text.toString().split("/")
                    campModel?.startDate =LocalDate.parse(listStartDate[0] +"-"  +listStartDate[1].padStart(2,'0') + "-" +  listStartDate[2].padStart(2,'0'))
                    val listEndDate = etEndDate.text.toString().split("/")
                    campModel?.endDate =LocalDate.parse(listEndDate[0] +"-"  +listEndDate[1].padStart(2,'0') + "-" +  listEndDate[2].padStart(2,'0'))
                }
            }

            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }

        btnDel.setOnClickListener {
            realm.executeTransaction{
               realm.where<CampModel>()
                    .equalTo("campId",getId).findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, CampMainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showDatePicker(tvCal:TextView) {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {_ , year, month, dayOfMonth->
                tvCal.text = "${year}/${month+1}/${dayOfMonth}"
            },
            startDbDate.year,
            startDbDate.monthValue-1,
            startDbDate.dayOfMonth)
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}