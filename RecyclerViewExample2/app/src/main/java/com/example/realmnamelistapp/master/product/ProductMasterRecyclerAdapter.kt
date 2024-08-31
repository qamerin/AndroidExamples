package com.example.realmnamelistapp.master.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.ProductMasterModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ProductMasterRecyclerAdapter(realmResults:RealmResults<ProductMasterModel>):RecyclerView.Adapter<ProductMasterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<ProductMasterModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductMasterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_product_master_layout,parent,false)
        return ProductMasterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ProductMasterViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvProductName.text = myModel?.productName.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<CategoryMasterModel>()
            .equalTo("categoryId",myModel?.categoryId).findFirst()
        holder.oneTvProductCategory.text = categoryMasterModelResult?.categoryName

        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context, GoodsMasterAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
//            intent.putExtra("goodsId",myModel?.goodsId)
//            it.context.startActivity(intent);
        }
    }
}