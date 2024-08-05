package com.example.realmnamelistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class EditActivity : AppCompatActivity() {
    //８）Realmの変数宣言
    private lateinit var realm :Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        //４）Viewの取得
        val etName:EditText = findViewById(R.id.etName)
        val etAge:EditText = findViewById(R.id.etAge)
        val btnSave:Button = findViewById(R.id.btnSave)
        val btnDel:Button = findViewById(R.id.btnDel)

        //８）ここでもRealmインスタンスを生成
        realm = Realm.getDefaultInstance()

        //９）Saveボタンを押したら～
        btnSave.setOnClickListener {
            //上書き用の変数を用意
            var name:String =""
            var age :Long = 0
            //１０）入力された文字が空文字でなければ～(変数に代入）
            if(!etName.text.isNullOrEmpty()){
                name =etName.text.toString()
            }
            if(!etAge.text.isNullOrEmpty()){
                age=etAge.text.toString().toLong()
            }

            //１１）【DBに書き込みますよ】 / Transaction{}
            realm.executeTransaction {
                val currentId = realm.where<MyModel>().max("id")//現時点のid(の最高値)を取得
                val nextId =(currentId?.toLong() ?:0L)+1L //最高値に１を追加（最高値が０なら１に）←行を追加するイメージ
                //モデルクラス(nextId番目)に値をセット
                val myModel =realm.createObject<MyModel>(nextId)
                myModel.name = name
                myModel.age = age
            }
            //１２）toastでメッセージを表示
            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    //８）Realm閉じる
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}