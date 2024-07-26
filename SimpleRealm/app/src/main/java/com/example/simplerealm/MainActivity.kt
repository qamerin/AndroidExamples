package com.example.simplerealm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.realm.Realm
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvName : TextView = findViewById(R.id.tvName)
        val btnAdd : Button = findViewById(R.id.btnAdd)
        val btnShow : Button = findViewById(R.id.btnShow)

        realm = Realm.getDefaultInstance()

        btnAdd.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
            tvName.text ="Name"

        }
        btnShow.setOnClickListener {
            val realmResult: List<MyModel> = realm.where(MyModel::class.java).findAll()
            for(i in realmResult.indices){
                tvName.append("\n")
                tvName.append(realmResult[i].name)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}