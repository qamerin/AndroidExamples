package com.qamerin.mycampapp.master.gear

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R

class GearMasterViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTvCampgroundName: TextView = v.findViewById(R.id.oneTvCampgoundName)
    var oneTvAddress: TextView = v.findViewById(R.id.oneTvAddress)

}