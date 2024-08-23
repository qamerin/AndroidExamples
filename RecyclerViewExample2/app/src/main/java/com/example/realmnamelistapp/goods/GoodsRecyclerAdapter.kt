package com.example.realmnamelistapp.goods

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.ViewHolderItem
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.RealmResults

class GoodsRecyclerAdapter(realmResults:RealmResults<GoodsModel>):RecyclerView.Adapter<ViewHolderItem>() {
    private val rResults:RealmResults<GoodsModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.goods_layout,parent,false)
        return ViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()
        holder.oneTvAge.text = myModel?.category.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, GoodsAddActivity::class.java)
            intent.putExtra("goodsId",myModel?.id)
            it.context.startActivity(intent);
        }
    }
}