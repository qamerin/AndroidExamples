package com.qamerin.mycampapp.campmaster

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.master.gear.CampgroundMasterRecyclerAdapter
import com.qamerin.mycampapp.model.CampgroundMasterModel
import io.realm.Realm
import io.realm.Sort

class CampgroundMasterActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter: CampgroundMasterRecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_campmaster)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ステータスバーの色を設定
        window.statusBarColor = ContextCompat.getColor(this, R.color.green_dark)

        realm = Realm.getDefaultInstance()

        val svProduct : SearchView = findViewById(R.id.svProduct)
        svProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val realmResults = realm.where(CampgroundMasterModel::class.java)
                    .contains("campgroundName",newText)
                    .findAll()
                    .sort("campgroundId", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

                recyclerView = findViewById(R.id.rvProduct)//ここでまずは中身recyclerViewにを入れる
                recyclerAdapter = CampgroundMasterRecyclerAdapter(realmResults)
                recyclerView.adapter = recyclerAdapter
                recyclerView.layoutManager = layoutManager

                return true
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val realmResults = realm.where(CampgroundMasterModel::class.java)
            .findAll().sort("campgroundId", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        recyclerView = findViewById(R.id.rvProduct)//ここでまずは中身recyclerViewにを入れる
        recyclerAdapter = CampgroundMasterRecyclerAdapter(realmResults)
        recyclerView.adapter = recyclerAdapter

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}