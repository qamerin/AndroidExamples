package com.example.realmnamelistapp.regulargear

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
import com.example.realmnamelistapp.master.gear.GearMasterActivity
import com.example.realmnamelistapp.model.RegularGearDetailModel
import com.example.realmnamelistapp.model.RegularGearModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where

class RegularGearDetailAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regulargeardetail_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val etName : TextView = findViewById(R.id.etGoodsName)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        val btnSearch : ImageView = findViewById(R.id.btnSearch)
        realm = Realm.getDefaultInstance()

        val myApp = MyApp.getInstance()
        val regularGearDetailId = myApp.regularGearDetailId

        val gearName = intent.getStringExtra("gearName")
        if(!gearName.isNullOrEmpty()) {
            etName.text = gearName
        }

        // set the spinner contents for Category
        val regularGearResult = realm.where(/* clazz = */ RegularGearModel::class.java)
            .findAll().sort("regularGearId", Sort.ASCENDING)//
        val regularGearList = ArrayList<RegularGearModel>()
        regularGearList.addAll(realm.copyFromRealm(regularGearResult));
        val regularGearAdapter = ArrayAdapter<RegularGearModel>(this, android.R.layout.simple_spinner_item, regularGearList)
        val regularGearSpinner = findViewById<Spinner>(R.id.spnCategory)
        regularGearSpinner.adapter = regularGearAdapter

        if(regularGearDetailId>0){
            val regularGearDetailModelResult = realm.where<RegularGearDetailModel>()
                .equalTo("regularGearDetailId",regularGearDetailId).findFirst()


            if(etName.text.isNullOrEmpty()){
                etName.text = regularGearDetailModelResult?.gearName
            }

            // get Category Name
            val regularGearModelResult = realm.where<RegularGearModel>()
                .equalTo("regularGearId",regularGearDetailModelResult?.regularGearId).findFirst()

            if (regularGearModelResult !=null) {
                regularGearSpinner.setSelection(regularGearModelResult.regularGearId.toInt())
            }

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        btnSearch.setOnClickListener {
            val intent = Intent(this, GearMasterActivity::class.java)
            myApp.prevPage = "REGULAR_GEAR_DETAIL"
            startActivity(intent)
        }
        btnSave.setOnClickListener {
            var regularGearId: Long = 0L
            val categoryModel = regularGearSpinner.selectedItem as RegularGearModel
            regularGearId = categoryModel.regularGearId
            if (regularGearDetailId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<RegularGearDetailModel>().max("regularGearDetailId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<RegularGearDetailModel>(nextId)
                    myModel.regularGearId = regularGearId
                    myModel.gearName = etName.text.toString()
                    myModel.isRegular = true
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<RegularGearDetailModel>()
                        .equalTo("regularGearDetailId", regularGearDetailId).findFirst()
                    myModel?.regularGearId = regularGearId
                    myModel?.gearName = etName.text.toString()
                    myModel?.isRegular = true
                }
            }

            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
        btnDel.setOnClickListener {
            realm.executeTransaction {
                val myModel = realm.where<RegularGearDetailModel>()
                    .equalTo("regularGearDetailId", regularGearDetailId).findFirst()
                myModel?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}