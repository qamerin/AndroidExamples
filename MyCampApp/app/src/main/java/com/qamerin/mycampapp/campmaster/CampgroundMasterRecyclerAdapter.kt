package com.qamerin.mycampapp.master.gear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.camp.CampEditActivity
import com.qamerin.mycampapp.model.CampgroundMasterModel
import io.realm.RealmResults

class CampgroundMasterRecyclerAdapter(
    private val startDate: String?,
    private val endDate: String?,
    realmResults:RealmResults<CampgroundMasterModel>
):RecyclerView.Adapter<CampgroundMasterViewHolderItem>() {
    private var rResults:RealmResults<CampgroundMasterModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampgroundMasterViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_camp_master_layout,parent,false)
        return CampgroundMasterViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: CampgroundMasterViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvCampgroundName.text = myModel?.campgroundName.toString()
        holder.oneTvAddress.text = myModel?.address.toString()

        holder.itemView.setOnClickListener {
                val intent = Intent(it.context, CampEditActivity::class.java)
                intent.putExtra("campgroundName",myModel?.campgroundName)
                intent.putExtra("campgroundAddress",myModel?.address)
                intent.putExtra("etStartDate",startDate)
                intent.putExtra("etEndDate",endDate)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                it.context.startActivity(intent);
        }

    }
    fun updateList(newItems: RealmResults<CampgroundMasterModel>) {
        rResults = newItems
        notifyDataSetChanged()
    }
}