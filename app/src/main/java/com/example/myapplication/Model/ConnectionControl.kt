package com.example.myapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ConnectionControl {

    @Expose
    @SerializedName("Hata")
    var hata: String? = null
    @Expose
    @SerializedName("Success")
    var success: Boolean = false
}
