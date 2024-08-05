package com.example.realmnamelistapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class EditActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etName : EditText = findViewById(R.id.etName)
        val etAge : EditText = findViewById(R.id.etAge)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)

        realm = Realm.getDefaultInstance()
        btnSave.setOnClickListener {
            var name:String = ""
            var age:Long = 0
            if(!etName.text.isNullOrEmpty()){
                name = etName.text.toString()
            }
            if(!etAge.text.isNullOrEmpty()){
                age = etAge.text.toString().toLong()
            }
            realm.executeTransaction {
                val currentId = realm.where<MyModel>().max("id")
                val nextId = (currentId?.toLong()?:0L) + 1L
                val myModel = realm.createObject<MyModel>(nextId)
                myModel.name = name
                myModel.age = age
            }
            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}