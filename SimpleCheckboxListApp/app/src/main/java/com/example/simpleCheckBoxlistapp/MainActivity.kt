package com.example.simpleCheckBoxlistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.realmnamelistapp.camp.SampleRecyclerAdapter
import io.realm.Realm
import io.realm.Sort

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var campRecyclerAdapter: SampleRecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnAdd: Button = findViewById(R.id.btnAdd)
        realm= Realm.getDefaultInstance()//７）realmのインスタンス生成
        btnAdd.setOnClickListener {
            val intent = Intent(this, SampleEditActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        val realmResults = realm.where(SampleModel::class.java)
            .findAll().sort("id", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        recyclerView = findViewById(R.id.rv)//ここでまずは中身recyclerViewにを入れる
        campRecyclerAdapter = SampleRecyclerAdapter(realmResults)
        recyclerView.adapter = campRecyclerAdapter

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}