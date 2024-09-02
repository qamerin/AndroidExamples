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
import com.example.realmnamelistapp.model.CampModel
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.time.LocalDate

class CampEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var registerDate:String
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
        val getId = intent.getLongExtra("ID",0L)
        if(getId>0){
            val campModelResult = realm.where<CampModel>()
                .equalTo("campId",getId).findFirst()
            etCampName.text = campModelResult?.campName.toString()
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
            var name:String = ""
            if(!etCampName.text.isNullOrEmpty()){
                name = etCampName.text.toString()
            }
            if(getId == 0L){
                realm.executeTransaction {
                    val currentId = realm.where<CampModel>().max("campId")
                    val nextId = (currentId?.toLong()?:0L) + 1L
                    val campModel = realm.createObject<CampModel>(nextId)
                    campModel.campName = name
                    campModel.startDate = LocalDate.parse(registerDate)
                }
            }else{
                realm.executeTransaction{
                    val campModel = realm.where<CampModel>()
                        .equalTo("campId",getId).findFirst()
                    campModel?.campName = name
                    campModel?.startDate = LocalDate.parse(registerDate)
                }
            }

            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }

        btnDel.setOnClickListener {
            realm.executeTransaction{
               realm.where<CampModel>()
                    .equalTo("id",getId).findFirst()?.deleteFromRealm()
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
                var list =tvCal.text.split("/")
                registerDate =list[0] +"-"  +list[1].padStart(2,'0') + "-" +  list[2].padStart(2,'0')
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