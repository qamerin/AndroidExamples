package com.qame.smartcamp.shopping

import ShoppingCategoryAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qame.smartcamp.R
import com.qame.smartcamp.common.MyApp
import com.qame.smartcamp.model.CampGearModel
import com.qame.smartcamp.model.ShoppingCategoryModel
import com.qame.smartcamp.model.ShoppingItemModel
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class ShoppingItemAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var checkboxContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shopping_item_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ステータスバーの色を設定
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val etShoppingItemName : TextView = findViewById(R.id.etShoppingItemName)
        val btnSave : Button = findViewById(R.id.btnSave)
        val btnDel : Button = findViewById(R.id.btnDel)
        realm = Realm.getDefaultInstance()
        val shoppingItemId = intent.getLongExtra("shoppingItemId",0L)
        val campId = MyApp.getInstance().campId

        if(shoppingItemId>0){
            val myModel = realm.where<ShoppingItemModel>()
                .equalTo("shoppingItemId", shoppingItemId).findFirst()
            etShoppingItemName.text = myModel?.shoppingItemName

            btnDel.visibility = View.VISIBLE

        }else{
            btnDel.visibility = View.INVISIBLE
        }

        btnSave.setOnClickListener {
            if (shoppingItemId == 0L) {
                realm.executeTransaction {
                    val currentId = realm.where<ShoppingItemModel>().max("shoppingItemId")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L
                    val myModel = realm.createObject<ShoppingItemModel>(nextId)
                    myModel.shoppingItemName =etShoppingItemName.text.toString()
                    myModel.campId = campId

                    // 選択されたgearCategoryIdを取得
                    val selectedCategoryId = adapter.getSelectedCategoryId()
                    myModel.shoppingCategoryId = selectedCategoryId
                }
            } else {
                realm.executeTransaction {
                    val myModel = realm.where<ShoppingItemModel>()
                        .equalTo("shoppingItemId", shoppingItemId).findFirst()
                    myModel?.shoppingItemName =etShoppingItemName.text.toString()

                    // 選択されたgearCategoryIdを取得
                    val selectedCategoryId = adapter.getSelectedCategoryId()
                    myModel?.shoppingCategoryId = selectedCategoryId
                }
            }

            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
        btnDel.setOnClickListener {
            realm.executeTransaction{
                realm.where<CampGearModel>()
                    .equalTo("campGearId", shoppingItemId).findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, ShoppingItemListActivity::class.java)
            startActivity(intent)
        }
        realm = Realm.getDefaultInstance()
        recyclerView = findViewById(R.id.recyclerView)

        // 画面幅に応じて列数を設定
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val columnCount = when {
            screenWidthDp >= 600 -> 3 // 600dp以上の幅の場合は3列
            screenWidthDp >= 400 -> 2 // 400dp以上の幅の場合は2列
            else -> 1 // それ以外の場合は1列
        }

        recyclerView.layoutManager = GridLayoutManager(this, columnCount)
        val shoppingItem = realm.where<ShoppingItemModel>().equalTo("shoppingItemId", shoppingItemId).findFirst()
        val selectedCategoryId = shoppingItem?.shoppingCategoryId ?: 0

        // ShoppingCategoryModelのデータを取得
        val shoppingCategories = realm.where<ShoppingCategoryModel>().findAll()

        // アダプタを設定
        adapter = ShoppingCategoryAdapter(this, shoppingCategories, selectedCategoryId)
        recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}