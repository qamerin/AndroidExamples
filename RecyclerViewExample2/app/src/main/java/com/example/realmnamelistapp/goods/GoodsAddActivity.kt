package com.example.realmnamelistapp.goods

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.GoodsMasterModel
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class GoodsAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goods_add)
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
        val getId = intent.getLongExtra("goodsId",0L)

        // set the spinner contents for Category
        val goodsResult = realm.where(/* clazz = */ GoodsMasterModel::class.java)
//            .equalTo("categoryId",goodsModelResult?.categoryId)
            .findAll()
            .sort("goodsId", Sort.ASCENDING)//
        val goodsList = ArrayList<GoodsMasterModel>()
        goodsList.addAll(realm.copyFromRealm(goodsResult));
        val goodsAdapter = ArrayAdapter<GoodsMasterModel>(this, android.R.layout.simple_spinner_item, goodsList)
        val goodsSpinner = findViewById<Spinner>(R.id.spnGoods)
        goodsSpinner.adapter = goodsAdapter



        // set the spinner contents for Category
        val categoryResult = realm.where(/* clazz = */ CategoryMasterModel::class.java)
            .findAll().sort("categoryId", Sort.ASCENDING)//
        val categoryList = ArrayList<CategoryMasterModel>()
        categoryList.addAll(realm.copyFromRealm(categoryResult));
        val categoryAdapter = ArrayAdapter<CategoryMasterModel>(this, android.R.layout.simple_spinner_item, categoryList)
        val categorySpinner = findViewById<Spinner>(R.id.spnCategory)
        categorySpinner.adapter = categoryAdapter

        if(getId>0){
            val goodsModelResult = realm.where<GoodsMasterModel>()
                .equalTo("goodsId",getId).findFirst()
//            etName.text = goodsModelResult?.name.toString()

            // get Category Name
            val categoryMasterModelResult = realm.where<CategoryMasterModel>()
                .equalTo("categoryId",goodsModelResult?.categoryId).findFirst()

            if (categoryMasterModelResult !=null) {
                categorySpinner.setSelection(categoryMasterModelResult.categoryId.toInt())
            }

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        btnSave.setOnClickListener {
            var name: String = ""
            var category: String = ""

           var goodsId = 0L
            val goodsModel = goodsSpinner.selectedItem as GoodsMasterModel
            goodsId = goodsModel.goodsId

            var categoryId: Long = 0L
            val categoryModel = categorySpinner.selectedItem as CategoryMasterModel
            categoryId = categoryModel.categoryId
            if (getId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<GoodsModel>().max("id")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<GoodsModel>(nextId)
                    myModel.goodsId = goodsId
                    myModel.categoryId = categoryId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<GoodsModel>()
                        .equalTo("id", getId).findFirst()
                    myModel?.goodsId = goodsId
                    myModel?.categoryId = categoryId
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