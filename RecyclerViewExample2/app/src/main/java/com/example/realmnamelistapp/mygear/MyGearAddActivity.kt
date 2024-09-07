package com.example.realmnamelistapp.mygear

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
import com.example.realmnamelistapp.master.product.ProductActivity
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.MyGearModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class MyGearAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mygear_add)
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
        val getId = intent.getLongExtra("myGearId",0L)

        val gearName = intent.getStringExtra("gearName")
        if(!gearName.isNullOrEmpty()) {
            etName.text = gearName
        }

        // set the spinner contents
        val result = realm.where(CampGearModel::class.java)
            .findAll().sort("campGearId", Sort.ASCENDING)//
        val list = ArrayList<CampGearModel>()
        list.addAll(realm.copyFromRealm(result));
        val adapter = ArrayAdapter<CampGearModel>(this, android.R.layout.simple_spinner_item, list)
        val spinner = findViewById<Spinner>(R.id.spnCategory)
        spinner.adapter = adapter

        if(getId>0){
            val goodsModelResult = realm.where<MyGearModel>()
                .equalTo("myGearId",getId).findFirst()
            etName.text = goodsModelResult?.gearName.toString()

            // get Category Name
            val campGearModelResult = realm.where<CampGearModel>()
                .equalTo("campGearId",goodsModelResult?.campGearId).findFirst()

            if (campGearModelResult !=null) {
                spinner.setSelection(campGearModelResult.campGearId.toInt())
            }

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        val categoryId = intent.getLongExtra("campGearId",0L)
        if(categoryId>0) {
            spinner.setSelection(categoryId.toInt())
        }

        btnSave.setOnClickListener {
            var name: String = ""
            var category: String = ""
            var categoryId: Long = 0L
            if (!etName.text.isNullOrEmpty()) {
                name = etName.text.toString()
            }
            val item = spinner.selectedItem as CampGearModel
            categoryId = item.campGearId
            if (getId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<MyGearModel>().max("myGearId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<MyGearModel>(nextId)
                    myModel.gearName = name
                    myModel.campGearId = categoryId
//                    myModel.campId = campId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<MyGearModel>()
                        .equalTo("myGearId", getId).findFirst()
                    myModel?.gearName = name
                    myModel?.campGearId = categoryId
//                    myModel?.campId = campId
                }
            }

            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSearch.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}