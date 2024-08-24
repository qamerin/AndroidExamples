package com.example.realmnamelistapp.goodsMaster

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class GoodsMasterViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
    var oneTvCategory: TextView = v.findViewById(R.id.oneTvCategory)

}