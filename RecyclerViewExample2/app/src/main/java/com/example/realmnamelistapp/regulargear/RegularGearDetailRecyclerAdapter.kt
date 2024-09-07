package com.example.realmnamelistapp.regulargear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.MyGearModel
import com.example.realmnamelistapp.model.CampGearDetailModel
import com.example.realmnamelistapp.model.RegularGearDetailModel
import com.example.realmnamelistapp.model.RegularGearModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class RegularGearDetailRecyclerAdapter(realmResults:RealmResults<RegularGearDetailModel>):RecyclerView.Adapter<RegularGearDetailViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<RegularGearDetailModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegularGearDetailViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_regulargeardetail_layout,parent,false)
        return RegularGearDetailViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: RegularGearDetailViewHolderItem, position: Int) {
        realm = Realm.getDefaultInstance()
        val myModel = rResults[position]
//        holder.oneTvName.text = myModel?.name.toString()

        val myGearModelResult = realm.where<RegularGearModel>()
            .equalTo("regularGearId",myModel?.regularGearId).findFirst()
        holder.oneTvName.text = myGearModelResult?.gearName
//        holder.oneTvName.text ="hogehoge"

        // get Category Name
        val campGearModelResult = realm.where<RegularGearModel>()
            .equalTo("regularGearId",myModel?.regularGearId).findFirst()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RegularGearDetailAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
            intent.putExtra("regularGearDetailId",myModel?.regularGearDetailId)
            it.context.startActivity(intent);
        }
    }
}