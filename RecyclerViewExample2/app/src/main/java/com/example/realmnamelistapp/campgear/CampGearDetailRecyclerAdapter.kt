package com.example.realmnamelistapp.campgear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CampGearDetailModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class CampGearDetailRecyclerAdapter(realmResults:RealmResults<CampGearDetailModel>):RecyclerView.Adapter<CampGearDetailViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<CampGearDetailModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampGearDetailViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_campgeardetail_layout,parent,false)
        return CampGearDetailViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: CampGearDetailViewHolderItem, position: Int) {
        realm = Realm.getDefaultInstance()
        val myModel = rResults[position]
        val campGearDetailModelResult = realm.where<CampGearDetailModel>()
            .equalTo("campGearId",myModel?.campGearId).findFirst()
        holder.oneTvName.text = campGearDetailModelResult?.campGearName

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CampGearDetailAddActivity::class.java)
            intent.putExtra("campGearDetailId",myModel?.campGearDetailId)
            it.context.startActivity(intent);
        }
    }
}