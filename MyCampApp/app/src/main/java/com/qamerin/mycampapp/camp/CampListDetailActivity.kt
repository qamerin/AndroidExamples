package com.qamerin.mycampapp.camp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.campgear.CampGearDetailListActivity
import com.qamerin.mycampapp.common.MyApp
//import com.qamerin.mycampapp.campgear.CampGearDetailListActivity
//import com.qamerin.mycampapp.master.gear.GearMasterActivity
//import com.qamerin.mycampapp.mygear.MyGearListActivity
import com.qamerin.mycampapp.model.CampModel
import com.qamerin.mycampapp.shopping.ShoppingItemListActivity
//import com.qamerin.mycampapp.regulargear.RegularGearDetailListActivity
import io.realm.Realm
import io.realm.kotlin.where

class CampListDetailActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camp_list_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ツールバーの表示
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // ステータスバーの色を設定
        window.statusBarColor = ContextCompat.getColor(this, R.color.green_dark)
        // ツールバーのタイトルを設定
        supportActionBar?.title = getString(R.string.camp_detail_name_label)
        // ツールバーに戻るボタンを設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        val etName : TextView = findViewById(R.id.etCampName)
        val etAddress : TextView = findViewById(R.id.etAddress)
        val etStartDate : TextView = findViewById(R.id.etStartDate)
        val etEndDate : TextView = findViewById(R.id.etEndDate)
        val btnModify: Button = findViewById(R.id.btnModify)
        val btnCampGear: Button = findViewById(R.id.btCampGear)
        realm = Realm.getDefaultInstance()
        val campId = MyApp.getInstance().campId
//        val getId = intent.getLongExtra("campId",0L)
        if(campId>0){
            val campModelResult = realm.where<CampModel>()
                .equalTo("campId",campId).findFirst()
            etName.text = campModelResult?.campName.toString()
            etAddress.text = campModelResult?.address.toString()
            etStartDate.text = campModelResult?.startDate?.year.toString()+
                    "/"+  campModelResult?.startDate?.monthValue.toString() +
                    "/" + campModelResult?.startDate?.dayOfMonth.toString()
            etEndDate.text = campModelResult?.endDate?.year.toString()+
                    "/"+  campModelResult?.endDate?.monthValue.toString() +
                    "/" + campModelResult?.endDate?.dayOfMonth.toString()

            btnModify.setOnClickListener {
                val intent = Intent(this, CampEditActivity::class.java)
//                intent.putExtra("campId",campModelResult?.campId)
                startActivity(intent)
            }
            btnCampGear.setOnClickListener {
                val intent = Intent(this, CampGearDetailListActivity::class.java)
//                intent.putExtra("campId",campModelResult?.campId)
                startActivity(intent)
            }
            val btShoppingItem: Button = findViewById(R.id.btShoppingItem)
            btShoppingItem.setOnClickListener {
                val intent = Intent(this, ShoppingItemListActivity::class.java)
                startActivity(intent)
            }
//            btnRegularGear.setOnClickListener {
//                val intent = Intent(this, RegularGearDetailListActivity::class.java)
//                startActivity(intent)
//            }
//            btnMyGear.setOnClickListener {
//                val intent = Intent(this, MyGearListActivity::class.java)
//                startActivity(intent)
//            }
//            btnGearMaster.setOnClickListener {
//                val intent = Intent(this, GearMasterActivity::class.java)
//                startActivity(intent)
//            }



        }else{
            // TODO
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}