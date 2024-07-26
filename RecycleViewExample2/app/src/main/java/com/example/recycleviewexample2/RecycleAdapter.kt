package com.example.recycleviewexample2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter:RecyclerView.Adapter<ViewHolderItem>() {
    //５）表示するデータを用意
     val nameList = listOf("My Name0","My Name1","My Name2","My Name3","My Name4","My Name5")
    //９）privateは消す
//    val nameList = listOf("My Name0","My Name1","My Name2","My Name3","My Name4","My Name5")
    private val messageList = listOf("メッセージ０","メッセージ１","メッセージ２","メッセージ３","メッセージ４","メッセージ５")
    private val imgList = listOf(
        R.drawable.img0,R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout,parent,false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.tvNameHolder.text = nameList[position]
        holder.tvMessageHolder.text = messageList[position]
        holder.ivHolder.setImageResource(imgList[position])
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

}