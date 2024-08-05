package com.example.realmnamelistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class RecyclerAdapter(realmResults:RealmResults<MyModel>):RecyclerView.Adapter<ViewHolderItem>() {
    private val rResults:RealmResults<MyModel> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout,parent,false)
        return ViewHolderItem(oneXml)
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()
        holder.oneTvAge.text = myModel?.age.toString()
    }
}