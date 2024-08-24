package com.example.realmnamelistapp.goods

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.ViewHolderItem
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class GoodsRecyclerAdapter(realmResults:RealmResults<GoodsModel>):RecyclerView.Adapter<GoodsViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<GoodsModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.goods_layout,parent,false)
        return GoodsViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: GoodsViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<CategoryMasterModel>()
            .equalTo("categoryId",myModel?.categoryId).findFirst()
        holder.oneTvCategory.text = categoryMasterModelResult?.categoryName

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, GoodsAddActivity::class.java)
            intent.putExtra("campId",myModel?.campId)
            intent.putExtra("goodsId",myModel?.id)
            it.context.startActivity(intent);
        }
    }
}