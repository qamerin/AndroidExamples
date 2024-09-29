package com.qamerin.mycampapp.camp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R

class CampViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
    var oneTvDate: TextView = v.findViewById(R.id.oneTvDate)

}