package com.example.realmnamelistapp.mygear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.MyGearModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class MyGearRecyclerAdapter(realmResults:RealmResults<MyGearModel>):RecyclerView.Adapter<MyGearViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<MyGearModel> = realmResults

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
        holder.oneTvName.text = myModel?.gearName.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val campGearModelResult = realm.where<CampGearModel>()
            .equalTo("campGearId",myModel?.campGearId).findFirst()
        holder.oneTvCategory.text = campGearModelResult?.campGearName

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, MyGearAddActivity::class.java)
//            intent.putExtra("campId",myModel?.campId)
            intent.putExtra("myGearId",myModel?.myGearId)
            it.context.startActivity(intent);
        }
    }
}