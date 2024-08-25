package com.example.realmnamelistapp.goods

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class CategoryRecyclerAdapter(realmResults:RealmResults<CategoryMasterModel>):RecyclerView.Adapter<CategoryViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<CategoryMasterModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_category_layout,parent,false)
        return CategoryViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolderItem, position: Int) {
        val myModel = rResults[position]
//        holder.oneTvName.text = myModel?.name.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<CategoryMasterModel>()
            .equalTo("categoryId",myModel?.categoryId).findFirst()
        holder.oneTvCategory.text = categoryMasterModelResult?.categoryName


        val goodsResult = realm.where<GoodsModel>()
            .findAll()
        val goodsAdapter = GoodsRecyclerAdapter(goodsResult)
        holder.child_recycler_view.layoutManager =LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL,false)
        holder.child_recycler_view.adapter = goodsAdapter

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, GoodsAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
//            intent.putExtra("goodsId",myModel?.id)
            it.context.startActivity(intent);
        }
    }
}