package com.qame.smartcamp.camp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qame.smartcamp.R
import com.qame.smartcamp.common.MyApp
import com.qame.smartcamp.model.CampModel
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
        holder.oneTvDate.text =  myModel?.startDate.toString().replace("-","/") +
                " 〜 " + myModel?.endDate.toString().replace("-","/")

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CampListDetailActivity::class.java)
            val myApp = MyApp.getInstance()
            myApp.campId = myModel!!.campId
            it.context.startActivity(intent);
        }
    }
}