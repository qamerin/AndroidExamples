package com.example.realmnamelistapp.camp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleCheckBoxlistapp.R
import com.example.simpleCheckBoxlistapp.SampleEditActivity
import com.example.simpleCheckBoxlistapp.SampleModel
import io.realm.RealmResults

class SampleRecyclerAdapter(realmResults:RealmResults<SampleModel>):RecyclerView.Adapter<SampleViewHolderItem>() {
    private val rResults:RealmResults<SampleModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_sample_layout,parent,false)
        return SampleViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: SampleViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.itemName.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, SampleEditActivity::class.java)
            intent.putExtra("id",myModel?.id)
            it.context.startActivity(intent);
        }
    }
}