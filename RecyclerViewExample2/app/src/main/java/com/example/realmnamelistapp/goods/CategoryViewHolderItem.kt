package com.example.realmnamelistapp.goods

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class CategoryViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvCategory: TextView = v.findViewById(R.id.oneTvCategory)
    var child_recycler_view: RecyclerView = v.findViewById(R.id.child_recycler_view)

}