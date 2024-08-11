package com.example.realmnamelistapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    }

    override fun onStart() {
        super.onStart()
                val etName : TextView = findViewById(R.id.etName)
        val etAge : TextView = findViewById(R.id.etAge)
        val btnModify: Button = findViewById(R.id.btnModify)
        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("ID",0L)
        if(getId>0){
            val myModelResult = realm.where<MyModel>()
                .equalTo("id",getId).findFirst()
            etName.text = myModelResult?.name.toString()
            etAge.text = myModelResult?.age.toString()

            btnModify.setOnClickListener {
                val intent = Intent(this,EditActivity::class.java)
                intent.putExtra("ID",myModelResult?.id)
                startActivity(intent)
            }
        }else{
            // TODO
        }

    }
}