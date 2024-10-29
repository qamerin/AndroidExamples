package com.qamerin.mycampapp.campgear

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.common.MyApp
import com.qamerin.mycampapp.model.CampGearModel
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

class CampGearDetailListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter: CampGearRecyclerAdapter
    private lateinit var layoutManager: LayoutManager
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchIsCarLoaded: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_campgeardetail_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ツールバーのタイトルを設定
        supportActionBar?.title = getString(R.string.camp_gear_label)
        // ステータスバーの色を設定
        window.statusBarColor = ContextCompat.getColor(this, R.color.green_dark)

        // realmのインスタンス生成
        realm= Realm.getDefaultInstance()

        //１）viewの取得
        val btnGoodsAdd: Button = findViewById(R.id.btnGoodsAdd)

        //６）btnAddを押したらintent
        btnGoodsAdd.setOnClickListener {
            val intent = Intent(this, CampGearDetailAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val campId = MyApp.getInstance().campId
        val realmResults = realm.where(CampGearModel::class.java)
            .equalTo("campId",campId)
            .findAll()
            .sort("campGearId", Sort.ASCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        recyclerView = findViewById(R.id.rvGoods)//ここでまずは中身recyclerViewにを入れる
        recyclerAdapter = CampGearRecyclerAdapter(realmResults)
        recyclerView.adapter = recyclerAdapter

        // Switchの状態に基づいてリストをフィルタリング
        switchIsCarLoaded = findViewById(R.id.switchIsNotCarLoaded)
        switchIsCarLoaded.setOnCheckedChangeListener { _, isChecked ->
            filterList(isChecked)
        }


        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    private fun filterList(isNotCarLoaded: Boolean) {
        val filteredList = if (isNotCarLoaded) {
            realm.where<CampGearModel>().equalTo("isCarLoaded",false).findAll()
        } else {
            realm.where<CampGearModel>().findAll()
        }
        recyclerAdapter.updateList(filteredList)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}
