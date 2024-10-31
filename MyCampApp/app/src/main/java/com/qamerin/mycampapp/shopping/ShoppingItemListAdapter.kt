package com.qamerin.mycampapp.shopping

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.model.ShoppingCategoryModel
import com.qamerin.mycampapp.model.ShoppingItemModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ShoppingItemListAdapter(
    realmResults: RealmResults<ShoppingItemModel>
    ) : RecyclerView.Adapter<ShoppingItemListViewHolderItem>() {
    private var rResults:RealmResults<ShoppingItemModel> = realmResults
    private lateinit var realm: Realm

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):    ShoppingItemListViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
        .inflate(R.layout.one_shopping_item_layout,parent,false)
        return ShoppingItemListViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ShoppingItemListViewHolderItem, position: Int) {
        val myModel = rResults[position]
        realm = Realm.getDefaultInstance()
        holder.oneTextItem.text = myModel?.shoppingItemName
        holder.boughtCheck.isChecked = myModel?.isItemBought ?: false

        realm.executeTransaction { realm ->
            val category = realm.where<ShoppingCategoryModel>()
                .equalTo("shoppingCategoryId", myModel?.shoppingCategoryId)
                .findFirst()
            category?.let {
                holder.oneTvShoppingCategoryName.text = it.categoryName
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ShoppingItemAddActivity::class.java)
            intent.putExtra("shoppingItemId",myModel?.shoppingItemId)
            it.context.startActivity(intent);
        }

        // carLoadCheckの状態を設定
        holder.boughtCheck.setOnClickListener{
            realm.executeTransaction { realm ->
                val gear = realm.where<ShoppingItemModel>()
                    .equalTo("shoppingItemId", myModel?.shoppingItemId)
                    .findFirst()
                gear?.let {
                    it.isItemBought = !it.isItemBought
                }
            }
            notifyDataSetChanged() // データセットの変更を通知してリフレッシュ
        }
    }
    fun updateList(newItems: RealmResults<ShoppingItemModel>) {
        rResults = newItems
        notifyDataSetChanged()
    }


}