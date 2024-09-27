package com.example.simplelistapp

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
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.time.LocalDate

class SampleEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var startDbDate:LocalDate = LocalDate.now()
    private var endDbDate:LocalDate = LocalDate.now()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sample_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etItemName : TextView = findViewById(R.id.etItemName)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)

        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("id",0L)
        if(getId>0){
            val campModelResult = realm.where<SampleModel>()
                .equalTo("id",getId).findFirst()
            etItemName.text = campModelResult?.itemName.toString()
            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }
        btnSave.setOnClickListener {
            var itemName:String = ""
            if(!etItemName.text.isNullOrEmpty()){
                itemName = etItemName.text.toString()
            }

            if(getId == 0L){
                realm.executeTransaction {
                    val currentId = realm.where<SampleModel>().max("id")
                    val nextCampId = (currentId?.toLong()?:0L) + 1L
                    val campModel = realm.createObject<SampleModel>(nextCampId)
                    campModel.itemName = itemName
                }
            }else{
                realm.executeTransaction{
                    val campModel = realm.where<SampleModel>()
                        .equalTo("id",getId).findFirst()
                    campModel?.itemName = itemName
                }
            }

            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }

        btnDel.setOnClickListener {
            realm.executeTransaction{
               realm.where<SampleModel>()
                    .equalTo("id",getId).findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
            finish()
            startActivity(intent)
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