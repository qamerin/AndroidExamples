package com.example.realmnamelistapp.goods.bulkregister

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class GoodsBulkRegisterViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
    var oneTvCategory: TextView = v.findViewById(R.id.oneTvCategory)

}