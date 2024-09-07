package com.example.realmnamelistapp.camp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.campgear.CampGearDetailListActivity
import com.example.realmnamelistapp.mygear.MyGearListActivity
import com.example.realmnamelistapp.model.CampModel
import io.realm.Realm
import io.realm.kotlin.where

class CampListDetailActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camp_list_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // ツールバーに戻るボタンを設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        val etName : TextView = findViewById(R.id.etCampName)
        val etAddress : TextView = findViewById(R.id.etAdress)
        val etStartDate : TextView = findViewById(R.id.etStartDate)
        val etEndDate : TextView = findViewById(R.id.etEndDate)
        val btnModify: Button = findViewById(R.id.btnModify)
        val btnMasterGoods: Button = findViewById(R.id.btGoodsMaster)
        val btnGoods: Button = findViewById(R.id.btGoods)
        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("campId",0L)
        if(getId>0){
            val campModelResult = realm.where<CampModel>()
                .equalTo("campId",getId).findFirst()
            etName.text = campModelResult?.campName.toString()
            etAddress.text = campModelResult?.address.toString()
            etStartDate.text = campModelResult?.startDate?.year.toString()+
                    "/"+  campModelResult?.startDate?.monthValue.toString() +
                    "/" + campModelResult?.startDate?.dayOfMonth.toString()
            etEndDate.text = campModelResult?.endDate?.year.toString()+
                    "/"+  campModelResult?.endDate?.monthValue.toString() +
                    "/" + campModelResult?.endDate?.dayOfMonth.toString()

            btnModify.setOnClickListener {
                val intent = Intent(this, CampEditActivity::class.java)
                intent.putExtra("campId",campModelResult?.campId)
                startActivity(intent)
            }
            btnMasterGoods.setOnClickListener {
                val intent = Intent(this, MyGearListActivity::class.java)
//                intent.putExtra("campId",myModelResult?.id)
                startActivity(intent)
            }
            btnGoods.setOnClickListener {
                val intent = Intent(this, CampGearDetailListActivity::class.java)
                intent.putExtra("campId",campModelResult?.campId)
                startActivity(intent)
            }

        }else{
            // TODO
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}