package com.example.realmnamelistapp.mygear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.MyModelModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class MyGearRecyclerAdapter(realmResults:RealmResults<MyModelModel>):RecyclerView.Adapter<MyGearViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<MyModelModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGearViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_mygear_layout,parent,false)
        return MyGearViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: MyGearViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<CategoryMasterModel>()
            .equalTo("categoryId",myModel?.categoryId).findFirst()
        holder.oneTvCategory.text = categoryMasterModelResult?.categoryName

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, MyGearAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
            intent.putExtra("goodsId",myModel?.goodsId)
            it.context.startActivity(intent);
        }
    }
}