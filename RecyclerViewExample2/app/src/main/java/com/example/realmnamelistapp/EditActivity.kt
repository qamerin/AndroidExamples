package com.example.realmnamelistapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realmnamelistapp.model.MyModel
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.time.LocalDate

class EditActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var registerDate:String
    private var dbDate:LocalDate = LocalDate.now()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // ツールバーに戻るボタンを設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val etName : TextView = findViewById(R.id.etName)
        val etAge : TextView = findViewById(R.id.etAge)
        val etDate : TextView = findViewById(R.id.etDate)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        val btnCal : Button = findViewById(R.id.button)

        btnCal.setOnClickListener {
            showDatePicker()
        }

        realm = Realm.getDefaultInstance()
        val getId = intent.getLongExtra("ID",0L)
        if(getId>0){
            val myModelResult = realm.where<MyModel>()
                .equalTo("id",getId).findFirst()
            etName.text = myModelResult?.name.toString()
            etAge.text = myModelResult?.age.toString()
            etDate.text = myModelResult?.day?.year.toString()+
                    "/"+  myModelResult?.day?.monthValue.toString() +
                    "/" + myModelResult?.day?.dayOfMonth.toString()
            dbDate = myModelResult?.day!!
            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }
        btnSave.setOnClickListener {
            var name:String = ""
            var age:Long = 0
            if(!etName.text.isNullOrEmpty()){
                name = etName.text.toString()
            }
            if(!etAge.text.isNullOrEmpty()){
                age = etAge.text.toString().toLong()
            }
            if(getId == 0L){
                realm.executeTransaction {
                    val currentId = realm.where<MyModel>().max("id")
                    val nextId = (currentId?.toLong()?:0L) + 1L
                    val myModel = realm.createObject<MyModel>(nextId)
                    myModel.name = name
                    myModel.age = age
                    myModel.day = LocalDate.parse(registerDate)
                }
            }else{
                realm.executeTransaction{
                    val myModel = realm.where<MyModel>()
                        .equalTo("id",getId).findFirst()
                    myModel?.name = name
                    myModel?.age = age
                    myModel?.day = LocalDate.parse(registerDate)
                }
            }

            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }

        btnDel.setOnClickListener {
            realm.executeTransaction{
               realm.where<MyModel>()
                    .equalTo("id",getId).findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showDatePicker() {
        val tvCal : TextView = findViewById(R.id.etDate)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {_ , year, month, dayOfMonth->
                tvCal.text = "${year}/${month+1}/${dayOfMonth}"
                var list =tvCal.text.split("/")
                registerDate =list[0] +"-"  +list[1].padStart(2,'0') + "-" +  list[2].padStart(2,'0')
            },
            dbDate.year,
            dbDate.monthValue-1,
            dbDate.dayOfMonth)
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}