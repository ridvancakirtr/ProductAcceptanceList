package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.SQLLITE.Model
import kotlinx.android.synthetic.main.seriesitemlist.view.*
import java.util.ArrayList

class SeriesKeepAdaptor(var ItemList: ArrayList<Model>): RecyclerView.Adapter<SeriesKeepAdaptor.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.seriesitemlist,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.series_miktar.text= ItemList[position].SeriAdi
        holder.series_name.text= ItemList[position].SeriMiktari.toString()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemViewCurrent=itemView as CardView

        var series_miktar=itemViewCurrent.series_miktar
        var series_name=itemViewCurrent.series_name
    }

}