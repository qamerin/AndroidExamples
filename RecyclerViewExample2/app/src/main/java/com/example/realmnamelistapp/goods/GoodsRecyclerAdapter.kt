package com.example.realmnamelistapp.goods

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.MyGearModel
import com.example.realmnamelistapp.model.CampGearDetailModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class GoodsRecyclerAdapter(realmResults:RealmResults<CampGearDetailModel>):RecyclerView.Adapter<GoodsViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<CampGearDetailModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_goods_layout,parent,false)
        return GoodsViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: GoodsViewHolderItem, position: Int) {
        realm = Realm.getDefaultInstance()
        val myModel = rResults[position]
//        holder.oneTvName.text = myModel?.name.toString()

        val myGearModelResult = realm.where<MyGearModel>()
            .equalTo("myGearId",myModel?.goodsId).findFirst()
        holder.oneTvName.text = myGearModelResult?.gearName
//        holder.oneTvName.text ="hogehoge"

        // get Category Name
        val campGearModelResult = realm.where<CampGearModel>()
            .equalTo("campGearId",myModel?.campGearId).findFirst()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, GoodsAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
            intent.putExtra("campGearDetailId",myModel?.campGearDetailId)
            it.context.startActivity(intent);
        }
    }
}