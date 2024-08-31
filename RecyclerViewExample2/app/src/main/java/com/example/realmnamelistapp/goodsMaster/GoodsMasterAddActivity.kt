package com.example.realmnamelistapp.goodsMaster

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
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.GoodsMasterModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class GoodsMasterAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goods_master_add)
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
        val getId = intent.getLongExtra("goodsId",0L)

        val productName = intent.getStringExtra("productName")
        if(!productName.isNullOrEmpty()) {
            etName.text = productName
        }

        // set the spinner contents
        val result = realm.where(CategoryMasterModel::class.java)
            .findAll().sort("categoryId", Sort.ASCENDING)//
        val list = ArrayList<CategoryMasterModel>()
        list.addAll(realm.copyFromRealm(result));
        val adapter = ArrayAdapter<CategoryMasterModel>(this, android.R.layout.simple_spinner_item, list)
        val spinner = findViewById<Spinner>(R.id.spnCategory)
        spinner.adapter = adapter

        if(getId>0){
            val goodsModelResult = realm.where<GoodsMasterModel>()
                .equalTo("goodsId",getId).findFirst()
            etName.text = goodsModelResult?.name.toString()

            // get Category Name
            val categoryMasterModelResult = realm.where<CategoryMasterModel>()
                .equalTo("categoryId",goodsModelResult?.categoryId).findFirst()

            if (categoryMasterModelResult !=null) {
                spinner.setSelection(categoryMasterModelResult.categoryId.toInt())
            }

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        val categoryId = intent.getLongExtra("categoryId",0L)
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
            val item = spinner.selectedItem as CategoryMasterModel
            categoryId = item.categoryId
            if (getId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<GoodsMasterModel>().max("goodsId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<GoodsMasterModel>(nextId)
                    myModel.name = name
                    myModel.categoryId = categoryId
//                    myModel.campId = campId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<GoodsMasterModel>()
                        .equalTo("goodsId", getId).findFirst()
                    myModel?.name = name
                    myModel?.categoryId = categoryId
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