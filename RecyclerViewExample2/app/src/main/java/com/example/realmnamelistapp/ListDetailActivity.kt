package com.example.realmnamelistapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.goods.GoodsListActivity
import com.example.realmnamelistapp.goodsMaster.GoodsMasterListActivity
import com.example.realmnamelistapp.model.MyModel
import io.realm.Realm
import io.realm.kotlin.where

class ListDetailActivity : AppCompatActivity() {
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
                val etName : TextView = findViewById(R.id.etName)
        val etAge : TextView = findViewById(R.id.etAge)
        val etDay : TextView = findViewById(R.id.etDay)
        val btnModify: Button = findViewById(R.id.btnModify)
        val btnMasterGoods: Button = findViewById(R.id.btGoodsMaster)
        val btnGoods: Button = findViewById(R.id.btGoods)
        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("ID",0L)
        if(getId>0){
            val myModelResult = realm.where<MyModel>()
                .equalTo("id",getId).findFirst()
            etName.text = myModelResult?.name.toString()
            etAge.text = myModelResult?.age.toString()
            etDay.text = myModelResult?.day?.year.toString()+
                    "/"+  myModelResult?.day?.monthValue.toString() +
                    "/" + myModelResult?.day?.dayOfMonth.toString()

            btnModify.setOnClickListener {
                val intent = Intent(this,EditActivity::class.java)
                intent.putExtra("ID",myModelResult?.id)
                startActivity(intent)
            }
            btnMasterGoods.setOnClickListener {
                val intent = Intent(this, GoodsMasterListActivity::class.java)
//                intent.putExtra("campId",myModelResult?.id)
                startActivity(intent)
            }
            btnGoods.setOnClickListener {
                val intent = Intent(this, GoodsListActivity::class.java)
                intent.putExtra("campId",myModelResult?.id)
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