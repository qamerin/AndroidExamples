package com.example.realmnamelistapp.goods.bulkregister

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.realmnamelistapp.R
import com.example.realmnamelistapp.common.MyApp
import com.example.realmnamelistapp.model.CategoryMasterModel
import com.example.realmnamelistapp.model.GoodsMasterModel
import com.example.realmnamelistapp.model.GoodsModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class GoodsBulkRegisterRecyclerAdapter(private val context: Context, realmResults:RealmResults<GoodsMasterModel>,
                                       private val isAlwaysSelectable: Boolean ):RecyclerView.Adapter<GoodsBulkRegisterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<GoodsMasterModel> = realmResults
    var selectedGoodsId = ArrayList<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsBulkRegisterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_goods_bulkregister_layout,parent,false)
        return GoodsBulkRegisterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: GoodsBulkRegisterViewHolderItem, position: Int) {
        realm = Realm.getDefaultInstance()
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()

        val campId = MyApp.getInstance().campId
        val goodsResult = realm.where<GoodsModel>()
            .equalTo("campId",campId).findAll()

        for(i in goodsResult){
            if(i.goodsId == myModel!!.goodsId){
                holder.myCheckBox.isChecked = true
                selectedGoodsId.add(i.goodsId)
            }
        }

        val teacher = rResults[position]

        // get Category Name
        val categoryMasterModelResult = realm.where<CategoryMasterModel>()
            .equalTo("categoryId",myModel?.categoryId).findFirst()
        holder.oneTvCategory.text = categoryMasterModelResult?.categoryName

        holder.setItemClickListener(object: GoodsBulkRegisterViewHolderItem.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val myCheckBox = v as CheckBox
                if (myCheckBox.isChecked) {
                    selectedGoodsId.add(myModel!!.goodsId)

                } else if (!myCheckBox.isChecked) {
                    selectedGoodsId.remove(myModel!!.goodsId)
                }
            }
        })

    }

}