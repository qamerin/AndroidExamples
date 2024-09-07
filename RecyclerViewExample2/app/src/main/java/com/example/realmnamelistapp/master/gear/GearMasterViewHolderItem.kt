package com.example.realmnamelistapp.master.gear

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class GearMasterViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvProductName: TextView = v.findViewById(R.id.oneTvProductName)
    var oneTvProductCategory: TextView = v.findViewById(R.id.oneTvProductCategory)

}