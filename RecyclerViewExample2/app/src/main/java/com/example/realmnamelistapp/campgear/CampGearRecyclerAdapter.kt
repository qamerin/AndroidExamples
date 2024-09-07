package com.example.realmnamelistapp.campgear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
//import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.CampGearDetailModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class CampGearRecyclerAdapter(realmResults:RealmResults<CampGearModel>):RecyclerView.Adapter<CampGearViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<CampGearModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampGearViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_campgear_layout,parent,false)
        return CampGearViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: CampGearViewHolderItem, position: Int) {
        val myModel = rResults[position]

        // get Category Name
        realm = Realm.getDefaultInstance()
        val categoryMasterModelResult = realm.where<CampGearModel>()
            .equalTo("campGearId",myModel?.campGearId).findFirst()
        holder.oneTvCategory.text = categoryMasterModelResult?.campGearName


        val campId = MyApp.getInstance().campId
        val goodsResult = realm.where<CampGearDetailModel>()
            .equalTo("campGearId",myModel?.campGearId)
            .equalTo("campId",campId)
            .findAll()
        val goodsAdapter = CampGearDetailRecyclerAdapter(goodsResult)
        holder.child_recycler_view.layoutManager =LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL,false)
        holder.child_recycler_view.adapter = goodsAdapter

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CampGearDetailAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
//            intent.putExtra("myGearId",myModel?.id)
            it.context.startActivity(intent);
        }
    }
}