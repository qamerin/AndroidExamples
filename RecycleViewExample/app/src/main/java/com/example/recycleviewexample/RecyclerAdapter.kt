package com.example.recycleviewexample

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter() :RecyclerView.Adapter<ViewHolderItem>() {
    // 表示するリストを用意
    val animalList = listOf("hogehoge","fugafuga","gomogomo")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout,parent,false)

        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.itemName.text = animalList[position]
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

}