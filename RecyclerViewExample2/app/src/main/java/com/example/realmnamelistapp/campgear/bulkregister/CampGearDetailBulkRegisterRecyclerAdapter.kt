package com.example.realmnamelistapp.campgear.bulkregister

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
import com.example.realmnamelistapp.model.CampGearModel
import com.example.realmnamelistapp.model.MyGearModel
import com.example.realmnamelistapp.model.CampGearDetailModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class CampGearDetailBulkRegisterRecyclerAdapter(private val context: Context, realmResults:RealmResults<MyGearModel>,
                                                private val isAlwaysSelectable: Boolean ):RecyclerView.Adapter<CampGearDetailBulkRegisterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<MyGearModel> = realmResults
    var selectedGoodsId = ArrayList<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampGearDetailBulkRegisterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_campgeardetail_bulkregister_layout,parent,false)
        return CampGearDetailBulkRegisterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: CampGearDetailBulkRegisterViewHolderItem, position: Int) {
        realm = Realm.getDefaultInstance()
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.gearName.toString()

        val campId = MyApp.getInstance().campId
        val goodsResult = realm.where<CampGearDetailModel>()
            .equalTo("campId",campId).findAll()

        for(i in goodsResult){
            if(i.goodsId == myModel!!.myGearId){
                holder.myCheckBox.isChecked = true
                selectedGoodsId.add(i.goodsId)
            }
        }

        val teacher = rResults[position]

        // get Category Name
        val campGearModelResult = realm.where<CampGearModel>()
            .equalTo("campGearId",myModel?.campGearId).findFirst()
        holder.oneTvCategory.text = campGearModelResult?.campGearName

        holder.setItemClickListener(object: CampGearDetailBulkRegisterViewHolderItem.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val myCheckBox = v as CheckBox
                if (myCheckBox.isChecked) {
                    selectedGoodsId.add(myModel!!.myGearId)

                } else if (!myCheckBox.isChecked) {
                    selectedGoodsId.remove(myModel!!.myGearId)
                }
            }
        })

    }

}