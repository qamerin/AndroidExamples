package com.example.realmnamelistapp.goods

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class GoodsViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
    var oneTvAge: TextView = v.findViewById(R.id.oneTvAge)

}