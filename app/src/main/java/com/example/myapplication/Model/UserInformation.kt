package com.example.myapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class UserInformation {
    @Expose
    @SerializedName("AdSoyad")
    val AdSoyad: String? = null
    @Expose
    @SerializedName("Kullanici_id")
    val Kullanici_id: Int = 0
    @Expose
    @SerializedName("Hata")
    val Hata: String? = null
    @Expose
    @SerializedName("Token")
    val Token: String? = null
    @Expose
    @SerializedName("Successful")
    val Successful: Boolean = false
}

