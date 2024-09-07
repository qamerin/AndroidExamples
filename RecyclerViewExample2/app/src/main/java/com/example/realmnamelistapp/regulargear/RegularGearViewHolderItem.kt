package com.example.realmnamelistapp.regulargear

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class RegularGearViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvRegularGear: TextView = v.findViewById(R.id.oneTvRegularGear)
    var regular_gear_recycler_view: RecyclerView = v.findViewById(R.id.regular_gear_recycler_view)

}