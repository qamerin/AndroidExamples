package com.example.realmnamelistapp.regulargear.bulkregister

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.RegularGearDetailModel
import com.example.realmnamelistapp.regulargear.RegularGearDetailListActivity
import com.example.realmnamelistapp.regularger.bulkregister.RegularGearDetailBulkRegisterRecyclerAdapter
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class RegularGearDetailBulkRegisterListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter: RegularGearDetailBulkRegisterRecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_campgeardetail_bulkregister_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // realmのインスタンス生成
        realm= Realm.getDefaultInstance()

        //１）viewの取得
        val btnGoodsAdd: Button = findViewById(R.id.btnRegister)

        //６）btnAddを押したらintent
        val campId = intent.getLongExtra("campId",0L)
        btnGoodsAdd.setOnClickListener {
            var checkedTeachers = recyclerAdapter.selectedGoodsId

            // if checkedTeachers does not contains goods, remove from goods.
            val deleteGoodsResult = realm.where<RegularGearDetailModel>()
                .equalTo("campId",campId).findAll()
            realm.executeTransaction {
                deleteGoodsResult.deleteAllFromRealm()
            }
            for(i in checkedTeachers){
                val goodsMasterResult = realm.where(/* clazz = */ RegularGearDetailModel::class.java)
                    .equalTo("myGearId",i).findFirst()

                realm.executeTransaction {
                    val currentId = realm.where<RegularGearDetailModel>().max("regularGearDetailId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<RegularGearDetailModel>(nextId)
                    myModel.regularGearDetailId = i
                    myModel.regularGearId = goodsMasterResult!!.regularGearId
                }
            }
            val intent = Intent(this, RegularGearDetailListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val realmResults = realm.where(RegularGearDetailModel::class.java)
            .findAll().sort("regularGearDetailId", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        recyclerView = findViewById(R.id.rvGoods)//ここでまずは中身recyclerViewにを入れる
        recyclerAdapter = RegularGearDetailBulkRegisterRecyclerAdapter(this, realmResults, false )
        recyclerView.adapter = recyclerAdapter

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}