package com.example.realmnamelistapp.goods

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
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.Realm
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

        val etName : TextView = findViewById(R.id.etGoodsName)
        val etCategory : TextView = findViewById(R.id.etCategory)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        realm = Realm.getDefaultInstance()
        val campId = intent.getLongExtra("campId",0L)
        val getId = intent.getLongExtra("goodsId",0L)
        if(getId>0){
            val goodsModelResult = realm.where<GoodsModel>()
                .equalTo("id",getId).findFirst()
            etName.text = goodsModelResult?.name.toString()
            etCategory.text = goodsModelResult?.category.toString()
            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        btnSave.setOnClickListener {
            var name: String = ""
            var category: String = ""
            if (!etName.text.isNullOrEmpty()) {
                name = etName.text.toString()
            }
            if (!etCategory.text.isNullOrEmpty()) {
                category = etCategory.text.toString()
            }
            if (getId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<GoodsModel>().max("id")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<GoodsModel>(nextId)
                    myModel.name = name
                    myModel.category = category
                    myModel.campId = campId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<GoodsModel>()
                        .equalTo("id", getId).findFirst()
                    myModel?.name = name
                    myModel?.category = category
                    myModel?.campId = campId
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