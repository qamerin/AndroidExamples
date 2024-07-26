package com.example.todoexample1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var addList= ArrayList<TodoData>()
    private lateinit var recyclerView:RecyclerView
    private var recycleAdapter=RecycleAdapter(addList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rv)
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

       val btnAdd : Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener{
            val et : EditText = findViewById(R.id.et)
            val data = TodoData(et.text.toString())
            addList.add(data)
            recycleAdapter.notifyItemInserted(addList.lastIndex)
            et.text = null
        }

    }
}