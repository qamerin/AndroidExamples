package com.example.realmnamelistapp.campgear

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.MyGearModel
import com.example.realmnamelistapp.model.CampGearDetailModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class CampGearDetailAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_campgeardetail_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val etName : TextView = findViewById(R.id.etGoodsName)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        realm = Realm.getDefaultInstance()
        val id = intent.getLongExtra("campId",0L)

        // set the spinner contents for Category
        val goodsResult = realm.where(/* clazz = */ MyGearModel::class.java)
//            .equalTo("campGearId",goodsModelResult?.campGearId)
            .findAll()
            .sort("myGearId", Sort.ASCENDING)//
        val goodsList = ArrayList<MyGearModel>()
        goodsList.addAll(realm.copyFromRealm(goodsResult));
        val goodsAdapter = ArrayAdapter<MyGearModel>(this, android.R.layout.simple_spinner_item, goodsList)
        val goodsSpinner = findViewById<Spinner>(R.id.spnGoods)
        goodsSpinner.adapter = goodsAdapter

        // set the spinner contents for Category
        val categoryResult = realm.where(/* clazz = */ CampGearModel::class.java)
            .findAll().sort("campGearId", Sort.ASCENDING)//
        val categoryList = ArrayList<CampGearModel>()
        categoryList.addAll(realm.copyFromRealm(categoryResult));
        val categoryAdapter = ArrayAdapter<CampGearModel>(this, android.R.layout.simple_spinner_item, categoryList)
        val categorySpinner = findViewById<Spinner>(R.id.spnCategory)
        categorySpinner.adapter = categoryAdapter

        if(id>0){
            val myModelResult = realm.where<CampGearDetailModel>()
                .equalTo("campGearDetailId",id).findFirst()

            // get Category Name
            val campGearModelResult = realm.where<CampGearModel>()
                .equalTo("campGearId",myModelResult?.campGearId).findFirst()

            if (campGearModelResult !=null) {
                categorySpinner.setSelection(campGearModelResult.campGearId.toInt())
            }

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        btnSave.setOnClickListener {
            var name: String = ""
            var category: String = ""

           var goodsId = 0L
            val goodsModel = goodsSpinner.selectedItem as MyGearModel
            goodsId = goodsModel.myGearId

            var categoryId: Long = 0L
            val categoryModel = categorySpinner.selectedItem as CampGearModel
            categoryId = categoryModel.campGearId
            if (goodsId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<CampGearDetailModel>().max("campGearDetailId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<CampGearDetailModel>(nextId)
                    myModel.goodsId = goodsId
                    myModel.campGearId = categoryId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<CampGearDetailModel>()
                        .equalTo("campGearDetailId", goodsId).findFirst()
                    myModel?.goodsId = goodsId
                    myModel?.campGearId = categoryId
                }
            }

            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
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