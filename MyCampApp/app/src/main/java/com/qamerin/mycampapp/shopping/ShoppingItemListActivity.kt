package com.qamerin.mycampapp.shopping

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.campgear.CampGearDetailAddActivity
import com.qamerin.mycampapp.common.MyApp
import com.qamerin.mycampapp.model.ShoppingItemModel
import io.realm.Realm
import io.realm.Sort

class ShoppingItemListActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LayoutManager
    private lateinit var recyclerAdapter: ShoppingItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shopping_item_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ツールバーのタイトルを設定
        supportActionBar?.title = getString(R.string.shopping_item_title_label)
        // ステータスバーの色を設定
        window.statusBarColor = ContextCompat.getColor(this, R.color.green_dark)

        realm = Realm.getDefaultInstance()

        //１）viewの取得
        val btnGoodsAdd: Button = findViewById(R.id.btnShoppingItemAdd)

        //６）btnAddを押したらintent
        btnGoodsAdd.setOnClickListener {
            val intent = Intent(this, ShoppingItemAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val campId = MyApp.getInstance().campId
        val realmResults = realm.where(ShoppingItemModel::class.java)
            .equalTo("campId",campId)
            .findAll().sort("shoppingItemId", Sort.DESCENDING)//上の数字が大くてだんだん小さくなる（上に追加する）

        // RecyclerViewの初期化をonCreate内で行う
        recyclerView = findViewById(R.id.rvShoppingItem)
        recyclerAdapter = ShoppingItemListAdapter(
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