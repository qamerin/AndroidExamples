package com.qamerin.mycampapp.camp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.campmaster.CampgroundMasterActivity
import com.qamerin.mycampapp.common.MyApp
import com.qamerin.mycampapp.model.CampGearDefaultModel
import com.qamerin.mycampapp.model.CampGearModel
import com.qamerin.mycampapp.model.CampModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CampEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var startDbDate:LocalDate = LocalDate.now()
    private var endDbDate:LocalDate = LocalDate.now()
    private lateinit var selectedDate: TextView
    private lateinit var datePicker: Button

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
        val etEndDate : TextView = findViewById(R.id.etEndDate)
        val btnSearch : ImageView = findViewById(R.id.btnSearch)

        realm = Realm.getDefaultInstance()
        val campId = MyApp.getInstance().campId
        if(campId>0){
            val campModelResult = realm.where<CampModel>()
                .equalTo("campId",campId).findFirst()
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
        val campgroundName = intent.getStringExtra("campgroundName")
        if(!campgroundName.isNullOrEmpty()) {
            etCampName.text = campgroundName
        }
        val campgroundAddress = intent.getStringExtra("campgroundAddress")
        if(!campgroundAddress.isNullOrEmpty()) {
            etAddress.text = campgroundAddress
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

            if(campId == 0L){
                // Add New Camp Record
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

                    // Add Camp Gear Record from Camp Default Gear
                    val campGearDefaultResult = realm.where(/* clazz = */ CampGearDefaultModel::class.java)
                        .findAll().sort("defaultCampGearId", Sort.ASCENDING)//
                    val defaultCampGearList = ArrayList<CampGearDefaultModel>()
                    defaultCampGearList.addAll(realm.copyFromRealm(campGearDefaultResult));
                    defaultCampGearList.forEach { defaultGearModel ->
                       val currentCampGearId = realm.where<CampGearModel>().max("campGearId")
                       val nextCampGearId = (currentCampGearId?.toLong()?:0L) + 1L
                       val newCampGearModel = realm.createObject<CampGearModel>(nextCampGearId)
                       newCampGearModel.campGearName = defaultGearModel.campGearName
                       newCampGearModel.gearCategoryId = defaultGearModel.gearCategoryId
                       newCampGearModel.campId = nextCampId
                   }
                }
            }else{
                realm.executeTransaction{
                    val campModel = realm.where<CampModel>()
                        .equalTo("campId",campId).findFirst()
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
                    .equalTo("campId",campId).findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, CampMainActivity::class.java)
            startActivity(intent)
        }
        btnSearch.setOnClickListener {
            val intent = Intent(this, CampgroundMasterActivity::class.java)
            intent.putExtra("etStartDate",etStartDate.text)
            intent.putExtra("etEndDate",etEndDate.text)
            startActivity(intent)
        }

        datePicker = findViewById(R.id.datePicker)

        datePicker.setOnClickListener {
            // 初期値として設定する日付を取得
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val initialStartDate = if (etStartDate.text.toString().isNotEmpty()) {
                val calendar = Calendar.getInstance().apply {
                    time = dateFormat.parse(etStartDate.text.toString())
                    add(Calendar.DAY_OF_MONTH, 1) // 1日進める
                }
                calendar.timeInMillis
            } else {
                // デフォルトの日付を設定（例：現在の日付）
                Calendar.getInstance().timeInMillis
            }

            val initialEndDate = if (etEndDate.text.toString().isNotEmpty()) {
                val calendar = Calendar.getInstance().apply {
                    time = dateFormat.parse(etEndDate.text.toString())
                    add(Calendar.DAY_OF_MONTH, 1) // 1日進める
                }
                calendar.timeInMillis
            } else {
                // デフォルトの日付を設定（例：現在の日付）
                Calendar.getInstance().timeInMillis
            }
            // DatePickerのビルダーを作成し、初期値を設定
            val datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
            datePickerBuilder.setSelection(androidx.core.util.Pair(initialStartDate, initialEndDate))
            val datePicker = datePickerBuilder.build()

            datePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when cancelled is clicked
             datePicker.addOnNegativeButtonClickListener {
                 Toast.makeText(this, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
             }
            datePicker.addOnPositiveButtonClickListener {  dateRange ->
                val startDate = dateRange.first
                val endDate = dateRange.second

                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val startDateString = dateFormat.format(Date(startDate))
                val endDateString = dateFormat.format(Date(endDate))

                Toast.makeText(this, "Selected: From $startDateString To $endDateString", Toast.LENGTH_LONG).show()

                etStartDate.text = startDateString
                etEndDate.text = endDateString

            }
            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
                Toast.makeText(this, "Date Picker Cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}