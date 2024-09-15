package com.example.realmnamelistapp.camp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class CampViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
    var oneTvDate: TextView = v.findViewById(R.id.oneTvDate)

}