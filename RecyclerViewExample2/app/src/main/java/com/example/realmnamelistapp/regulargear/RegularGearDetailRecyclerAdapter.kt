package com.example.realmnamelistapp.regulargear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
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
        holder.oneTvName.text = myModel?.gearName.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RegularGearDetailAddActivity::class.java)
//            intent.putExtra("regularGearDetailId",myModel?.regularGearDetailId)
            val myApp = MyApp.getInstance()
            myApp.regularGearDetailId = myModel?.regularGearDetailId!!
//            intent.putExtra("regularGearId",myModel?.regularGearId)
            it.context.startActivity(intent);
        }
    }
}