package com.qamerin.mycampapp.campmaster

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
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
    private lateinit var startDate: String
    private lateinit var endDate: String

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
        // ツールバーのタイトルを設定
        supportActionBar?.title = getString(R.string.campground_master_label)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ステータスバーの色を設定
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        realm = Realm.getDefaultInstance()

        intent.getStringExtra("etStartDate").let {
            startDate = it.toString()
        }
        intent.getStringExtra("etEndDate").let {
            endDate = it.toString()
        }

        val svProduct : SearchView = findViewById(R.id.svProduct)

        // SearchViewのレイアウトが完全に初期化された後に子ビューを取得
        svProduct.post {
            val searchEditText = svProduct.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText?.setTextAppearance(R.style.SearchViewTextStyle)
        }

        svProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val realmResults = realm.where(CampgroundMasterModel::class.java)
                    .contains("campgroundName",newText)
                    .or()
                    .contains("address", newText)
                    .findAll()
                    .sort("dispSeq", Sort.ASCENDING)

                recyclerView = findViewById(R.id.rvProduct)//ここでまずは中身recyclerViewにを入れる
                recyclerAdapter = CampgroundMasterRecyclerAdapter(
                    startDate,
                    endDate,
                    realmResults)
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
            .findAll()
            .sort("dispSeq", Sort.ASCENDING)

        recyclerView = findViewById(R.id.rvProduct)//ここでまずは中身recyclerViewにを入れる
        recyclerAdapter = CampgroundMasterRecyclerAdapter(
            startDate,
            endDate,
            realmResults
        )
        recyclerView.adapter = recyclerAdapter

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}