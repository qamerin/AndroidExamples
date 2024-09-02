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
import com.example.realmnamelistapp.goods.GoodsListActivity
import com.example.realmnamelistapp.goodsMaster.GoodsMasterListActivity
import com.example.realmnamelistapp.model.CampModel
import io.realm.Realm
import io.realm.kotlin.where

class CampListDetailActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_detail)
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
//        val etAge : TextView = findViewById(R.id.etAge)
        val etDay : TextView = findViewById(R.id.etStartDate)
        val btnModify: Button = findViewById(R.id.btnModify)
        val btnMasterGoods: Button = findViewById(R.id.btGoodsMaster)
        val btnGoods: Button = findViewById(R.id.btGoods)
        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("ID",0L)
        if(getId>0){
            val campModelResult = realm.where<CampModel>()
                .equalTo("campId",getId).findFirst()
            etName.text = campModelResult?.campName.toString()
//            etAge.text = campModelResult?.age.toString()
            etDay.text = campModelResult?.startDate?.year.toString()+
                    "/"+  campModelResult?.startDate?.monthValue.toString() +
                    "/" + campModelResult?.startDate?.dayOfMonth.toString()

            btnModify.setOnClickListener {
                val intent = Intent(this, CampEditActivity::class.java)
                intent.putExtra("ID",campModelResult?.campId)
                startActivity(intent)
            }
            btnMasterGoods.setOnClickListener {
                val intent = Intent(this, GoodsMasterListActivity::class.java)
//                intent.putExtra("campId",myModelResult?.id)
                startActivity(intent)
            }
            btnGoods.setOnClickListener {
                val intent = Intent(this, GoodsListActivity::class.java)
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