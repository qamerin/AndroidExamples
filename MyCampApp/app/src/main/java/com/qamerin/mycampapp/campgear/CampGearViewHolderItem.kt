package com.qame.smartcamp.campgear

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qame.smartcamp.R

class CampGearViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvCampGearName: TextView = v.findViewById(R.id.oneTvCampGearName)
    var oneTvCategory: TextView = v.findViewById(R.id.oneTvCategoryName)
    var carLoadCheck: CheckBox = v.findViewById(R.id.carLoadCheck)
}