package com.example.realmnamelistapp.regulargear.bulkregister

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R

class RegularGearDetailBulkRegisterViewHolderItem(v:View) :RecyclerView.ViewHolder(v), View.OnClickListener{
    var oneTvName: TextView = v.findViewById(R.id.oneTvName)
    var oneTvCategory: TextView = v.findViewById(R.id.oneTvRegularGear)
    var myCheckBox: CheckBox = v.findViewById(R.id.myCheckBox)

    lateinit var myItemClickListener: ItemClickListener

    init {
        myCheckBox.setOnClickListener(this)
    }

    fun setItemClickListener(ic: ItemClickListener) {
        this.myItemClickListener = ic
    }

    override fun onClick(v: View) {
        this.myItemClickListener.onItemClick(v, layoutPosition)
    }

    interface ItemClickListener {

        fun onItemClick(v: View, pos: Int)
    }

//    myCheckBox.setOnClickListener(this)

}