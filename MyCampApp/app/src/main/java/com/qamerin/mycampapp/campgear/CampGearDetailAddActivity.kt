package com.qamerin.mycampapp.campgear

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
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.common.MyApp
import com.qamerin.mycampapp.model.CampGearModel
import io.realm.Realm
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

        val etGearName : TextView = findViewById(R.id.etGearName)
        val etCategoryName : TextView = findViewById(R.id.etGearCategory)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        realm = Realm.getDefaultInstance()
        val campGearId = intent.getLongExtra("campGearId",0L)
        val campId = MyApp.getInstance().campId

        if(campGearId>0){
            val myModel = realm.where<CampGearModel>()
                .equalTo("campGearId", campGearId).findFirst()
            etGearName.text = myModel?.campGearName
            etCategoryName.text = myModel?.categoryName

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        btnSave.setOnClickListener {
            if (campGearId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<CampGearModel>().max("campGearId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<CampGearModel>(nextId)
                    myModel.campGearName =etGearName.text.toString()
                    myModel.categoryName = etCategoryName.text.toString()
                    myModel.campId = campId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<CampGearModel>()
                        .equalTo("campGearId", campGearId).findFirst()
                    myModel?.campGearName =etGearName.text.toString()
                    myModel?.categoryName = etCategoryName.text.toString()
                }
            }

            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
        btnDel.setOnClickListener {
            realm.executeTransaction{
                realm.where<CampGearModel>()
                    .equalTo("campGearId", campGearId).findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, CampGearDetailListActivity::class.java)
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