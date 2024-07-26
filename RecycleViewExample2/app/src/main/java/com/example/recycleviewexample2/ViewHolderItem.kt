package com.example.recycleviewexample2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ViewHolderItem(itemView: View) :RecyclerView.ViewHolder(itemView){
    val ivHolder: ImageView = itemView.findViewById(R.id.iv)
    val tvNameHolder: TextView = itemView.findViewById(R.id.tvName)
    val tvMessageHolder: TextView = itemView.findViewById(R.id.tvMessage)

    private val recycleAdapter = RecycleAdapter()
    private val nameList = recycleAdapter.nameList
    init{
        itemView.setOnClickListener {
            val position:Int = adapterPosition
            Toast.makeText(itemView.context,"${nameList[position]}さんです！",Toast.LENGTH_LONG).show()
        }
    }

}