package com.qamerin.mycampapp.shopping

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R

class ShoppingItemListViewHolderItem(v:View) :RecyclerView.ViewHolder(v){
    var oneTextItem: TextView = v.findViewById(R.id.tvShoppingItemName)
    var oneTvShoppingCategoryName: TextView = v.findViewById(R.id.oneTvShoppingCategoryName)
    var boughtCheck: CheckBox = v.findViewById(R.id.boughtCheck)

}