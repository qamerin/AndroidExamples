package com.qamerin.mycampapp.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.model.ShoppingItemModel
import io.realm.RealmResults

class ShoppingItemListAdapter(
    realmResults: RealmResults<ShoppingItemModel>
    ) : RecyclerView.Adapter<ShoppingItemListViewHolderItem>() {
    private val rResults:RealmResults<ShoppingItemModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):    ShoppingItemListViewHolderItem {
    val oneXml = LayoutInflater.from(parent.context)
        .inflate(R.layout.one_shopping_item_layout,parent,false)
        return ShoppingItemListViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ShoppingItemListViewHolderItem, position: Int) {
        val item = rResults[position]
        holder.oneTextItem.text = item?.shoppingItemName

//        holder.buttonUpdate.setOnClickListener {
//            items[position] = holder.editTextItem.text.toString()
//            notifyItemChanged(position)
//        }
    }


}