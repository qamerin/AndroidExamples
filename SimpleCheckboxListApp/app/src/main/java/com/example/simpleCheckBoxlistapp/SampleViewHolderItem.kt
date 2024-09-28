package com.example.realmnamelistapp.camp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleCheckBoxlistapp.R

class SampleViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)

}