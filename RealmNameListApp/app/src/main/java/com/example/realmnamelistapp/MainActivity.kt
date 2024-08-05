package com.example.realmnamelistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import io.realm.Realm
import io.realm.Sort


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
//    private lateinit var recycleAdapter: RecycleAdapter
    private lateinit var recyclerAdapter:RecyclerAdapter
    private lateinit var layoutManager: LayoutManager

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        val btnAdd: Button = findViewById(R.id.btnAdd)
//        realm=Realm.getDefaultInstance()
//
//        btnAdd.setOnClickListener {
//            Toast.makeText(applicationContext,"hoge",Toast.LENGTH_SHORT).show()
//            val intent = Intent(this,EditActivity::class.java)
//            startActivity(intent)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //１）viewの取得
        val btnAdd:Button = findViewById(R.id.btnAdd)
        realm= Realm.getDefaultInstance()//７）realmのインスタンス生成

        //６）btnAddを押したらintent
        btnAdd.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
        }
    }


//    override fun onStart() {
//        super.onStart()
//        val realmResults = realm.where(MyModel::class.java)
//            .findAll().sort("id",Sort.DESCENDING)
//        recyclerView = findViewById(R.id.rv)
//        recycleAdapter = RecycleAdapter(realmResults)
//        recyclerView.adapter = recycleAdapter
//
//        layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//    }
//２０）Activityが開始された時にその都度処理される
override fun onStart() {
    super.onStart()
    //【表示しますよ】
    //モデルクラスの全件を見つけて、ソート（並べ替え）をかける
    val realmResults = realm.where(MyModel::class.java)
        .findAll().sort("id",Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

    //アダプターに結果を入れますよ
    recyclerView = findViewById(R.id.rv)//ここでまずは中身recyclerViewにを入れる
    recyclerAdapter = RecyclerAdapter(realmResults)
    recyclerView.adapter = recyclerAdapter
//
//    //縦並びに配置しますよ
    layoutManager = LinearLayoutManager(this)
//    recyclerView.layoutManager = layoutManager
//    recyclerView.layoutManager = layoutManager

}

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}