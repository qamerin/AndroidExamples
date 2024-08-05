package com.example.realmnamelistapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderItem(v:View):RecyclerView.ViewHolder(v) {
    //View(xml)の方から、指定のidを見つけてくる(v経由で)
    var oneTvName:TextView =v.findViewById(R.id.oneTvName)
    var oneTvAge:TextView =v.findViewById(R.id.oneTvAge)
}