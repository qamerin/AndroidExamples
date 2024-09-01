package com.example.realmnamelistapp.goods.bulkregister

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.GoodsMasterModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class GoodsBulkRegisterRecyclerAdapter(realmResults:RealmResults<GoodsMasterModel>):RecyclerView.Adapter<GoodsBulkRegisterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<GoodsMasterModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsBulkRegisterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_goods_bulkregister_layout,parent,false)
        return GoodsBulkRegisterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: GoodsBulkRegisterViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<CategoryMasterModel>()
            .equalTo("categoryId",myModel?.categoryId).findFirst()
        holder.oneTvCategory.text = categoryMasterModelResult?.categoryName

        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context, GoodsMasterAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
//            intent.putExtra("goodsId",myModel?.goodsId)
//            it.context.startActivity(intent);
        }
    }
}