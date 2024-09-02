package com.example.realmnamelistapp.goods.bulkregister

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
import com.example.realmnamelistapp.goods.GoodsListActivity
import com.example.realmnamelistapp.model.MyModelModel
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class GoodsBulkRegisterListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter: GoodsBulkRegisterRecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goods_bulkregister_list)
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
            val deleteGoodsResult = realm.where<GoodsModel>()
                .equalTo("campId",campId).findAll()
            realm.executeTransaction {
                deleteGoodsResult.deleteAllFromRealm()
            }
            for(i in checkedTeachers){
                val goodsMasterResult = realm.where(/* clazz = */ MyModelModel::class.java)
                    .equalTo("goodsId",i).findFirst()

                realm.executeTransaction {
                    val currentId = realm.where<GoodsModel>().max("id")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<GoodsModel>(nextId)
                    myModel.goodsId = i
                    myModel.campId = campId
                    myModel.categoryId = goodsMasterResult!!.categoryId
                }
            }
            val intent = Intent(this, GoodsListActivity::class.java)
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
        val realmResults = realm.where(MyModelModel::class.java)
            .findAll().sort("goodsId", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        recyclerView = findViewById(R.id.rvGoods)//ここでまずは中身recyclerViewにを入れる
        recyclerAdapter = GoodsBulkRegisterRecyclerAdapter(this, realmResults, false )
        recyclerView.adapter = recyclerAdapter

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}