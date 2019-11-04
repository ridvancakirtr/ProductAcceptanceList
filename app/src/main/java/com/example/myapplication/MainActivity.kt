package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.example.myapplication.Responce.Responce
import com.example.myapplication.Retrofit.Retrofit
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adaptor.ItemAcceptAdaptor
import com.example.myapplication.Model.*
import com.example.myapplication.SQLLITE.DBHelper
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    val db by lazy { DBHelper(this) }
    lateinit var USER_ID: String
    lateinit var NAME_SURNAME: String
    lateinit var TOKEN: String
    lateinit var CARI_KOD: String
    var MalKabulList = ArrayList<SeriesPostDataModel.MALKABULLISTESIEntity>()
    var seriesList = ArrayList<SeriesPostDataModel.SERI_LISTEntity>()
    val allItems = ArrayList<CurrentListModel>()
    val itemAcceptItems = ArrayList<ItemAcceptListModel>()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar=supportActionBar
        actionBar!!.title="Mal Kabul Listesi"


        progressBar=findViewById(R.id.progressBar)
        getUserData()
        cariListesi(USER_ID, TOKEN)

        current.setOnClickListener {
            provideSimpleDialog()

        }

        datetable!!.setOnClickListener {
            dateSetting()
        }


        provideSimpleDialog()
}

    @SuppressLint("SetTextI18n")
    private fun dateSetting() {
        val now = Calendar.getInstance()
        val year = now.get(Calendar.YEAR)
        val month = now.get(Calendar.MONTH)
        val day = now.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
            var monthString = i2.toString()
            if (monthString.length == 1) {
                monthString = "0$monthString"
            }

            var dayString = i3.toString()
            if (dayString.length == 1) {
                dayString = "0$dayString"
            }
            datetable!!.setText("$i-$monthString-$dayString")
            documentNumber.requestFocus()
        }, year, month, day).show()
    }

    private fun saveData() {
        if (current.text.isEmpty() || documentNumber.text.isEmpty() || datetable.text.isEmpty()){
            val contextView = findViewById<View>(android.R.id.content)
            val snackbar =
                Snackbar.make(contextView, "Boş Alanları Doldurunuz", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }else{
        var data = db.readDataINC()
        var total=0
        var isAdd=false
        for (k in itemAcceptItems.iterator()) {
            //Log.d("RESULT tag", k.mINCKEYNO.toString())
            for (x in data) {
                if (x.KEYNO == k.mINCKEYNO!!.toInt()) {
                    Log.d("RESULT tag","Eşleşen Key = "+k.mINCKEYNO)
                    seriesList.add(SeriesPostDataModel.SERI_LISTEntity(x.SeriMiktari,x.SeriAdi))
                    total+=(x.SeriMiktari)
                    Log.d("RESULT tag","Seri Miktarı = "+x.SeriMiktari)
                    isAdd=true
                }
            }
            if (isAdd){
                MalKabulList.add(SeriesPostDataModel.MALKABULLISTESIEntity(seriesList, total, k.mINCKEYNO!!.toInt()))
                seriesList = ArrayList()
                isAdd=false
                Log.d("RESULT tag","Toplam  = "+total)
                Log.d("RESULT tag","-------------------------------")
                total=0
            }
        }

        //val a = SeriesPostDataModel.SERI_LISTEntity(7, "12")

        val c = SeriesPostDataModel.Mk_kaydetEntity(
            MalKabulList,
            documentNumber.text.toString(),
            datetable.text.toString(),
            CARI_KOD
        )
        val d = SeriesPostDataModel(c, TOKEN, USER_ID)

        val gson = Gson()

        val json = gson.toJson(d)

        println(json)
        progressBar!!.visibility=View.VISIBLE

        postData(d)
        MalKabulList.clear()
        }
    }

    private fun postData(seriesPostDataModel: SeriesPostDataModel) {
        Retrofit().getClient(getRetrofitURL()).create(Responce::class.java)
            .postSeries(seriesPostDataModel).enqueue(object : Callback<SeriesRespondDataModel> {
                override fun onFailure(call: Call<SeriesRespondDataModel>, t: Throwable) {
                    Log.d("." +
                            " FAIL", t.message.toString())
                }

                override fun onResponse(
                    call: Call<SeriesRespondDataModel>,
                    response: Response<SeriesRespondDataModel>
                ) {
                    Log.d(
                        "RESULT OK",
                        "" + response.body()!!.mSuccess + " > " + response.body()!!.mHata + " > " + call.request()
                    )
                    showErrorMessage(response.body()!!.mHata)
                    progressBar!!.visibility=View.GONE
                    //MalKabulList.clear()
                    //seriesList.clear()
                }
            })
    }

    private fun showErrorMessage(mHata: String?) {
        val contextView = findViewById<View>(android.R.id.content)
        val snackbar =
            Snackbar.make(contextView, "$mHata", Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    fun provideSimpleDialog() {
        val dialog = SimpleSearchDialogCompat(this@MainActivity, "Arama...",
            "Cari Alış Listesini Seçiniz...", null, allItems,
            SearchResultListener { dialog, item, position ->
                current.setText(item.value)
                itemAcceptItems.clear()
                CARI_KOD = item.value
                malKabul(USER_ID, TOKEN, CARI_KOD)
                dialog.dismiss()
                dateSetting()
            }
        )
        dialog.show()
        dialog.searchBox.typeface = Typeface.SERIF
    }

    fun zeroAdd() {
        var zero = ""
        val eksik = 14 - documentNumber.text.length
        for (item in 0..eksik)
            zero += 0
        zero += documentNumber.text
        documentNumber.setText(zero)
    }

    private fun getRetrofitURL(): String {
        val sharedPref = this.getSharedPreferences("LoginActivity", Context.MODE_PRIVATE)
        val ipaddress = sharedPref.getString("IP_ADDRESS", "Kayıt Yok")
        return ipaddress!!
    }

    private fun cariListesi(USER_ID: String, TOKEN: String) {
        Retrofit().getClient(getRetrofitURL()).create(Responce::class.java)
            .getCurrentValues(USER_ID, TOKEN).enqueue(object : Callback<Current> {
                override fun onFailure(call: Call<Current>, t: Throwable) {
                    Log.d("TAG FAIL", t.message + " > " + call.request())
                }

                override fun onResponse(call: Call<Current>, response: Response<Current>) {
                    Log.d("TAG OK", response.isSuccessful.toString() + " > " + call.request())
                    val a = response.body()!!.Data
                    val size = response.body()?.Data?.size
                    for (x in 0 until size!!) {
                        allItems.add(
                            CurrentListModel(
                                a?.get(x)?.TEXT.toString(),
                                a?.get(x)?.VALUE.toString()
                            )
                        )
                    }

                }
            })
    }

    private fun malKabul(USER_ID: String, TOKEN: String, CARI_KOD: String) {
        Retrofit().getClient(getRetrofitURL()).create(Responce::class.java)
            .getMalKabul(USER_ID, TOKEN, CARI_KOD).enqueue(object : Callback<ItemAccept> {
                override fun onFailure(call: Call<ItemAccept>, t: Throwable) {
                    Log.d("FAIL malKabul", t.message + " > " + call.request())
                }

                override fun onResponse(call: Call<ItemAccept>, response: Response<ItemAccept>) {
                    Log.d("OK malKabul", response.isSuccessful.toString() + " > " + call.request())
                    val a = response.body()!!.mData
                    Log.d( "TEST ->",a.toString())
                    val size = response.body()?.mData?.size

                    for (x in 0 until size!!) {
                        itemAcceptItems.add(
                            ItemAcceptListModel(
                                a!![x].mGIRIS_SERI,
                                a[x].mFISNO,
                                a[x].mSIPARIS_TEST,
                                a[x].mTARIH,
                                a[x].mTESLIM_MIK.toString(),
                                a[x].mSIPARIS_MIK.toString(),
                                a[x].mSTOK_ADI,
                                a[x].mSTOK_KODU,
                                a[x].mINCKEYNO.toString()
                            )
                        )

                    }
                    Log.d("SORGU YAPILDI", "")
                    recyclerView.adapter = ItemAcceptAdaptor(itemAcceptItems)
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    (recyclerView.adapter as ItemAcceptAdaptor).notifyDataSetChanged()
                }
            })
    }

    private fun getUserData() {
        val sharedPref = this.getSharedPreferences("LoginActivity", Context.MODE_PRIVATE)
        this.USER_ID = sharedPref.getString("USER_ID", "Kayıt Yok")!!
        this.NAME_SURNAME = sharedPref.getString("NAME_SURNAME", "Kayıt Yok")!!
        this.TOKEN = sharedPref.getString("TOKEN", "Kayıt Yok")!!
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_series->{
                zeroAdd()
                saveData()
            }
        }

        return super.onContextItemSelected(item)
    }
}