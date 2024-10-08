package com.example.realmnamelistapp.master.gear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
import com.example.realmnamelistapp.mygear.MyGearAddActivity
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.GearMasterModel
import com.example.realmnamelistapp.regulargear.RegularGearDetailAddActivity
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class GearMasterRecyclerAdapter(realmResults:RealmResults<GearMasterModel>):RecyclerView.Adapter<GearMasterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<GearMasterModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearMasterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_gear_master_layout,parent,false)
        return GearMasterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: GearMasterViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvProductName.text = myModel?.gearName.toString()

        // get Category Name
        realm = Realm.getDefaultInstance()
        val campGearModelResult = realm.where<CampGearModel>()
            .equalTo("campGearId",myModel?.campGearId).findFirst()
        holder.oneTvProductCategory.text = campGearModelResult?.campGearName

        holder.itemView.setOnClickListener {

            // TODO
            val prevPage = MyApp.getInstance().prevPage
            if(prevPage == "REGULAR_GEAR_DETAIL"){
                val intent = Intent(it.context, RegularGearDetailAddActivity::class.java)
                intent.putExtra("gearName",myModel?.gearName)
                intent.putExtra("campGearId",myModel?.campGearId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                it.context.startActivity(intent);
            }else{
                val intent = Intent(it.context, MyGearAddActivity::class.java)
                intent.putExtra("gearName",myModel?.gearName)
                intent.putExtra("campGearId",myModel?.campGearId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                it.context.startActivity(intent);
            }



//            val intent = Intent(it.context, MyGearAddActivity::class.java)
//            intent.putExtra("gearName",myModel?.gearName)
//            intent.putExtra("campGearId",myModel?.campGearId)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            it.context.startActivity(intent);
        }

    }
}