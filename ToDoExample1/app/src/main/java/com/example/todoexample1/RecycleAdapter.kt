package com.example.todoexample1

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(private val todoList:ArrayList<TodoData>) : RecyclerView.Adapter<RecycleAdapter.ViewHolderItem>(){
    inner class ViewHolderItem(v: View,rAdapter:RecycleAdapter) : RecyclerView.ViewHolder(v){
        val tvHolder : TextView = v.findViewById(R.id.tv)
        init {
            v.setOnClickListener {
                val positon : Int = adapterPosition
                val listPosition = todoList[positon]

               AlertDialog.Builder(v.context)
                   .setTitle("${listPosition.myToDo}")
                   .setMessage("本当に削除しますか")
                   .setPositiveButton("Yes") { _, _ ->
                           todoList.removeAt(positon)
                           rAdapter.notifyItemRemoved(positon)

                       }
                   .setNegativeButton("No",null)
                   .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
       val itemXml = LayoutInflater.from(parent.context).inflate(R.layout.one_layout,parent,false)
        return ViewHolderItem(itemXml,this)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val currentItem = todoList[position]
        holder.tvHolder.text = currentItem.myToDo
    }

}