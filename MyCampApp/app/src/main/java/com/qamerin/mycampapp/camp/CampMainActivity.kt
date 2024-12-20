package com.qame.smartcamp.camp

import DividerItemDecoration
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.qame.smartcamp.R
import com.qame.smartcamp.common.MyApp
import com.qame.smartcamp.model.CampModel
import io.realm.Realm
import io.realm.Sort


class CampMainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var campRecyclerAdapter: CampRecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camp_main)

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        // ナビゲーションアイコンを設定
        toolbar.setNavigationIcon(R.mipmap.ic_launcher)

        //１）viewの取得
        val btnAdd:Button = findViewById(R.id.btnAdd)
        realm= Realm.getDefaultInstance()//７）realmのインスタンス生成

        //６）btnAddを押したらintent
        btnAdd.setOnClickListener {
            val myApp = MyApp.getInstance()
            myApp.campId = 0L
            val intent = Intent(this, CampEditActivity::class.java)
            startActivity(intent)
        }
    }


override fun onStart() {
    super.onStart()
    val realmResults = realm.where(CampModel::class.java)
        .findAll().sort("campId",Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

    recyclerView = findViewById(R.id.rv)//ここでまずは中身recyclerViewにを入れる
    campRecyclerAdapter = CampRecyclerAdapter(realmResults)
    recyclerView.adapter = campRecyclerAdapter

    // カスタムdividerを追加
    val dividerItemDecoration = DividerItemDecoration(this, R.drawable.custom_divider)
    recyclerView.addItemDecoration(dividerItemDecoration)

    layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager

}

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}