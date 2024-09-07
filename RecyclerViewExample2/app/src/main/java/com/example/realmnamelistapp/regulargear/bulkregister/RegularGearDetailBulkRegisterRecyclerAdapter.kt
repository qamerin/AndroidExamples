package com.example.realmnamelistapp.regularger.bulkregister

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
import com.example.realmnamelistapp.model.RegularGearDetailModel
import com.example.realmnamelistapp.model.RegularGearModel
import com.example.realmnamelistapp.regulargear.bulkregister.RegularGearDetailBulkRegisterViewHolderItem
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class RegularGearDetailBulkRegisterRecyclerAdapter(private val context: Context, realmResults:RealmResults<RegularGearDetailModel>,
                                                private val isAlwaysSelectable: Boolean ):RecyclerView.Adapter<RegularGearDetailBulkRegisterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<RegularGearDetailModel> = realmResults
    var selectedGoodsId = ArrayList<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegularGearDetailBulkRegisterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_campgeardetail_bulkregister_layout,parent,false)
        return RegularGearDetailBulkRegisterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: RegularGearDetailBulkRegisterViewHolderItem, position: Int) {
        realm = Realm.getDefaultInstance()
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.gearName.toString()

        val campId = MyApp.getInstance().campId
        val goodsResult = realm.where<RegularGearDetailModel>()
            .equalTo("campId",campId).findAll()

        for(i in goodsResult){
            if(i.regularGearDetailId == myModel!!.regularGearDetailId){
                holder.myCheckBox.isChecked = true
                selectedGoodsId.add(i.regularGearDetailId)
            }
        }

        val teacher = rResults[position]

        // get Category Name
        val campGearModelResult = realm.where<RegularGearModel>()
            .equalTo("regularGearId",myModel?.regularGearId).findFirst()
        holder.oneTvCategory.text = campGearModelResult?.gearName

        holder.setItemClickListener(object: RegularGearDetailBulkRegisterViewHolderItem.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val myCheckBox = v as CheckBox
                if (myCheckBox.isChecked) {
                    selectedGoodsId.add(myModel!!.regularGearDetailId)

                } else if (!myCheckBox.isChecked) {
                    selectedGoodsId.remove(myModel!!.regularGearDetailId)
                }
            }
        })

    }

}