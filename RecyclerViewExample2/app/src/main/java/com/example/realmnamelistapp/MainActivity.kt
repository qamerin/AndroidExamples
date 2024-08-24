package com.example.realmnamelistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.realmnamelistapp.model.MyModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter:RecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        //１）viewの取得
        val btnAdd:Button = findViewById(R.id.btnAdd)
        realm= Realm.getDefaultInstance()//７）realmのインスタンス生成

        //６）btnAddを押したらintent
        btnAdd.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
        }
    }


override fun onStart() {
    super.onStart()
    val realmResults = realm.where(MyModel::class.java)
        .findAll().sort("id",Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

    recyclerView = findViewById(R.id.rv)//ここでまずは中身recyclerViewにを入れる
    recyclerAdapter = RecyclerAdapter(realmResults)
    recyclerView.adapter = recyclerAdapter

    layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager

}

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}