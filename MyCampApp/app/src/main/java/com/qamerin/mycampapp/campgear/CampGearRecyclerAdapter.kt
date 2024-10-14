package com.qamerin.mycampapp.campgear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.model.CampGearModel
import com.qamerin.mycampapp.model.GearCategoryModel
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
        holder.oneTvCampGearName.text = myModel?.campGearName
        holder.carLoadCheck.isChecked = myModel?.isCarLoaded ?: false

        realm.executeTransaction { realm ->
            val category = realm.where<GearCategoryModel>()
                .equalTo("gearCategoryId", myModel?.gearCategoryId)
                .findFirst()
            category?.let {
                holder.oneTvCategory.text = it.categoryName
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CampGearDetailAddActivity::class.java)
            intent.putExtra("campGearId",myModel?.campGearId)
            it.context.startActivity(intent);
        }

        // carLoadCheckの状態を設定
        holder.carLoadCheck.setOnClickListener{
            realm.executeTransaction { realm ->
                val gear = realm.where<CampGearModel>()
                    .equalTo("campGearId", myModel?.campGearId)
                    .findFirst()
                gear?.let {
                    it.isCarLoaded = !it.isCarLoaded
                }
            }
        }
    }
}