package com.example.recycleviewexample

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView){
    val itemName: TextView = itemView.findViewById(R.id.tv)

    // click Action
    private val recyclerAdapter = RecyclerAdapter()
    private  val animalList = recyclerAdapter.animalList

    init {
        itemView.setOnClickListener{
            val position: Int = adapterPosition
            Toast.makeText(itemView.context,animalList[position],Toast.LENGTH_SHORT).show()
        }
    }
}