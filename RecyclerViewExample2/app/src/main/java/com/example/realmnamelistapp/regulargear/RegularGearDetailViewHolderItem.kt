package com.example.realmnamelistapp.regulargear

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class RegularGearDetailViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
}