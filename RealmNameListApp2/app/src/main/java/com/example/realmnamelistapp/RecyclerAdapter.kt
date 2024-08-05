package com.example.realmnamelistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

//１５）Adapter
//１７-１）結果を受け取る引数（＝受け皿）RealmResults ← 登録データ全件(Realmクエリの実行結果)を取得する
class RecyclerAdapter(realmResults:RealmResults<MyModel>):RecyclerView.Adapter<ViewHolderItem>() {
    //１７-２）RecyclerAdapter()の引数realmResultsを展開していく変数を用意
    private val rResults:RealmResults<MyModel> =realmResults

    //１６）1行だけのレイアウト表示
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val  oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout,parent,false)
        return ViewHolderItem(oneXml)
    }

    //１７-３）position番目のデータを表示
    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val myModel = rResults[position]//position番目の結果を取得
        holder.oneTvName.text = myModel?.name.toString() //position番目のnameを代入
        holder.oneTvAge.text = myModel?.age.toString()//position番目のageを代入
    }

    //１８）リスト（＝結果件数）の件数（＝サイズ）
    override fun getItemCount(): Int {
        return rResults.size
    }
}