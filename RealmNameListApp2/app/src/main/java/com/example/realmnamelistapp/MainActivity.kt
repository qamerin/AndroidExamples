package com.example.realmnamelistapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    //１）viewの取得(中身は後で代入する)
    private lateinit var recyclerView:RecyclerView
    private lateinit var realm:Realm//７）realmの変数を用意
    //１９）【RecyclerView】AdapterとLayoutManagerの変数を準備
    private lateinit var recyclerAdapter:RecyclerAdapter
    private lateinit var layoutManager:LayoutManager

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

        //縦並びに配置しますよ
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    //７）Realmを閉じる
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}