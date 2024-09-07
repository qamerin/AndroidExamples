package com.example.realmnamelistapp.regulargear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
import com.example.realmnamelistapp.model.RegularGearDetailModel
import com.example.realmnamelistapp.model.RegularGearModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class RegularGearRecyclerAdapter(realmResults:RealmResults<RegularGearModel>):RecyclerView.Adapter<RegularGearViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<RegularGearModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegularGearViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_regulargear_layout,parent,false)
        return RegularGearViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: RegularGearViewHolderItem, position: Int) {
        val myModel = rResults[position]

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<RegularGearModel>()
            .equalTo("regularGearId",myModel?.regularGearId).findFirst()
        holder.oneTvRegularGear.text = categoryMasterModelResult?.gearName


        val campId = MyApp.getInstance().campId
        val regularGearResult = realm.where<RegularGearDetailModel>()
            .equalTo("regularGearId",myModel?.regularGearId)
            .findAll()
        val goodsAdapter = RegularGearDetailRecyclerAdapter(regularGearResult)
        holder.regular_gear_recycler_view.layoutManager =LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL,false)
        holder.regular_gear_recycler_view.adapter = goodsAdapter

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RegularGearDetailAddActivity::class.java)
            it.context.startActivity(intent);
        }
    }
}