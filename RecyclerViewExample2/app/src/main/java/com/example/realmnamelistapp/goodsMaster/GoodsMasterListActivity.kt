package com.example.realmnamelistapp.goodsMaster

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
import com.example.realmnamelistapp.model.GoodsMasterModel
import io.realm.Realm
import io.realm.Sort

class GoodsMasterListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter: GoodsMasterRecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goods_list)
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
        val btnGoodsAdd: Button = findViewById(R.id.btnGoodsAdd)

        //６）btnAddを押したらintent
        val campId = intent.getLongExtra("campId",0L)
        btnGoodsAdd.setOnClickListener {
            val intent = Intent(this, GoodsMasterAddActivity::class.java)
            intent.putExtra("campId",campId)
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
        val realmResults = realm.where(GoodsMasterModel::class.java)
//            .equalTo("campId" ,1L)
            .findAll().sort("id", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        recyclerView = findViewById(R.id.rvGoods)//ここでまずは中身recyclerViewにを入れる
        recyclerAdapter = GoodsMasterRecyclerAdapter(realmResults)
        recyclerView.adapter = recyclerAdapter

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}