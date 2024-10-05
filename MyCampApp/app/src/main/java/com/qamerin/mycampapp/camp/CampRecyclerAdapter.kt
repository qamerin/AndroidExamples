package com.qamerin.mycampapp.camp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.common.MyApp
import com.qamerin.mycampapp.model.CampModel
import io.realm.RealmResults

class CampRecyclerAdapter(realmResults:RealmResults<CampModel>):RecyclerView.Adapter<CampViewHolderItem>() {
    private val rResults:RealmResults<CampModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_camp_layout,parent,false)
        return CampViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: CampViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.campName.toString()
        holder.oneTvDate.text = myModel?.startDate.toString() +
                " 〜 " + myModel?.endDate.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CampListDetailActivity::class.java)
            val myApp = MyApp.getInstance()
            myApp.campId = myModel!!.campId
            it.context.startActivity(intent);
        }
    }
}