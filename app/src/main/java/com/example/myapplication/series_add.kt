package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.ItemAcceptAdaptor
import com.example.myapplication.Adaptor.SeriesKeepAdaptor
import com.example.myapplication.Adaptor.SwipeToDeleteCallback
import com.example.myapplication.Model.*
import com.example.myapplication.Responce.Responce
import com.example.myapplication.Retrofit.Retrofit
import com.example.myapplication.SQLLITE.DBHelper
import com.example.myapplication.SQLLITE.Model
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_series_add.*
import kotlinx.android.synthetic.main.activity_series_add.recyclerView
import kotlinx.android.synthetic.main.product_item.view.*
import kotlinx.android.synthetic.main.seriesitemlist.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class series_add : AppCompatActivity() {
    val db by lazy { DBHelper(this)  }
    var INCKEYNO:Int=0
    var seriListesi:ArrayList<Model> = ArrayList()
    var seriListINC:ArrayList<SeriesPostDataModel.MALKABULLISTESIEntity> = ArrayList()
    var toplam:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_add)

        val actionBar=supportActionBar
        actionBar!!.title="Seri Seçimi"
        actionBar.setDisplayHomeAsUpEnabled(true)

        INCKEYNO=intent.getStringExtra("INCKEYNO")!!.toInt()


        seriListesi=db.readData(INCKEYNO)
        val adaptor=SeriesKeepAdaptor(seriListesi)
        recyclerView.adapter= adaptor
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        btnseriEkle.setOnClickListener {
            seriListesi.add(Model(edtxseries_miktar.text.toString().toInt(),edtxseries_name.text.toString()))
            db.insertData(Model(INCKEYNO,edtxseries_miktar.text.toString().toInt(),edtxseries_name.text.toString()))
            adaptor.notifyDataSetChanged()
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d("SWIPE",""+viewHolder.adapterPosition)
                db.deleteData(viewHolder.adapterPosition,seriListesi)
                seriListesi.removeAt(viewHolder.adapterPosition)
                adaptor.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
