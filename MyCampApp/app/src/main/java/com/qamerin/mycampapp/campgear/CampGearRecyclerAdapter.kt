package com.qamerin.mycampapp.campgear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.common.MyApp
import com.qamerin.mycampapp.model.CampGearModel
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
        val campId = MyApp.getInstance().campId
        val categoryMasterModelResult = realm.where<CampGearModel>()
            .equalTo("campId",campId)
            .equalTo("campGearId",myModel?.campGearId).findFirst()
        holder.oneTvCampGearName.text = categoryMasterModelResult?.campGearName
        holder.oneTvCategory.text = categoryMasterModelResult?.categoryName

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CampGearDetailAddActivity::class.java)
            intent.putExtra("campGearId",myModel?.campGearId)
            it.context.startActivity(intent);
        }
    }
}