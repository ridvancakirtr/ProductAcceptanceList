package com.example.myapplication.Adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.ItemAcceptListModel
import com.example.myapplication.R
import com.example.myapplication.series_add
import kotlinx.android.synthetic.main.product_item.view.*
import java.util.ArrayList

class ItemAcceptAdaptor(var ItemList: ArrayList<ItemAcceptListModel>): RecyclerView.Adapter<ItemAcceptAdaptor.CurrentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return CurrentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ItemList.size
    }

    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        holder.siparis_tarihi.text= ItemList[position].mTARIH!!.replace("T00:00:00","")
        holder.teslim_tarihi.text= ItemList[position].mSIPARIS_TEST!!.replace("T00:00:00","")
        holder.siparis_numarasi.text= ItemList[position].mFISNO
        holder.siparis_miktar.text= ItemList[position].mSIPARIS_MIK
        holder.onceki_teslim_miktar.text= ItemList[position].mTESLIM_MIK
        //holder.teslim_alinacak_miktar.text= ItemList[position].mTESLIM_MIK
        holder.stok_adi.text= ItemList[position].mSTOK_ADI
        holder.stok_kodu.text= ItemList[position].mSTOK_KODU
        holder.seri.text=isSeries(ItemList[position].mGIRIS_SERI)
        holder.seri.setOnClickListener {
                //Toast.makeText(holder.itemViewCurrent.context,"TIKLADI",Toast.LENGTH_LONG).show()
                val homeIntent = Intent(holder.itemViewCurrent.context, series_add::class.java)
                homeIntent.putExtra("INCKEYNO", ItemList[position].mINCKEYNO)
                holder.itemView.context.startActivity(homeIntent)
        }


    }

    private fun isSeries(mgirisSeri: String?): String? {
        return if (mgirisSeri=="E") "SERİ EKLE" else ""
    }


    inner class CurrentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemViewCurrent=itemView as CardView

        var siparis_tarihi=itemViewCurrent.siparis_tarihi
        var teslim_tarihi=itemViewCurrent.teslim_tarihi
        var siparis_numarasi=itemViewCurrent.siparis_numarasi
        var siparis_miktar=itemViewCurrent.siparis_miktar
        var onceki_teslim_miktar=itemViewCurrent.onceki_teslim_miktar
        var teslim_alinacak_miktar=itemViewCurrent.teslim_alinacak_miktar
        var stok_adi=itemViewCurrent.stok_adi
        var stok_kodu=itemViewCurrent.stok_kodu
        var seri=itemViewCurrent.seri
    }

}