package com.example.myapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeriesRespondDataModel {

    @Expose
    @SerializedName("Data")
    val mData: List<String>? = null
    @Expose
    @SerializedName("Hata")
    val mHata: String? = null
    @Expose
    @SerializedName("Success")
    val mSuccess: Boolean = false
}
