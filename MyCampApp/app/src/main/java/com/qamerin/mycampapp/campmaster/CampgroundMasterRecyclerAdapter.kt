package com.qamerin.mycampapp.master.gear

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.camp.CampEditActivity
import com.qamerin.mycampapp.common.MyApp
//import com.qamerin.mycampapp.mygear.MyGearAddActivity
import com.qamerin.mycampapp.model.CampgroundMasterModel
//import com.qamerin.mycampapp.model.GearMasterModel
//import com.qamerin.mycampapp.regulargear.RegularGearDetailAddActivity
import io.realm.Realm
import io.realm.RealmResults

class CampgroundMasterRecyclerAdapter(
    private val startDate: String?,
    private val endDate: String?,
    realmResults:RealmResults<CampgroundMasterModel>
):RecyclerView.Adapter<CampgroundMasterViewHolderItem>() {
    private lateinit var realm: Realm
    private val rResults:RealmResults<CampgroundMasterModel> = realmResults

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

            // TODO
//            val prevPage = MyApp.getInstance().prevPage
//            if(prevPage == "REGULAR_GEAR_DETAIL"){
//                val intent = Intent(it.context, RegularGearDetailAddActivity::class.java)
//                intent.putExtra("gearName",myModel?.gearName)
//                intent.putExtra("campGearId",myModel?.campGearId)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                it.context.startActivity(intent);
//            }else{

                val intent = Intent(it.context, CampEditActivity::class.java)
                intent.putExtra("campgroundName",myModel?.campgroundName)
                intent.putExtra("campgroundAddress",myModel?.address)
                intent.putExtra("etStartDate",startDate)
                intent.putExtra("etEndDate",endDate)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                it.context.startActivity(intent);
//            }



//            val intent = Intent(it.context, MyGearAddActivity::class.java)
//            intent.putExtra("gearName",myModel?.gearName)
//            intent.putExtra("campGearId",myModel?.campGearId)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            it.context.startActivity(intent);
        }

    }
}