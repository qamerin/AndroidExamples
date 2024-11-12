package com.qame.smartcamp.master.gear

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qame.smartcamp.R

class CampgroundMasterViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvCampgroundName: TextView = v.findViewById(R.id.oneTvCampgroundName)
    var oneTvAddress: TextView = v.findViewById(R.id.oneTvAddress)

}