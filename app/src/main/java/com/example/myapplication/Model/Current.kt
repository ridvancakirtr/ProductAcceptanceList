package com.example.myapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.mirrajabi.searchdialog.core.Searchable

class Current {

    @Expose
    @SerializedName("Data")
    var Data: List<DataEntity>? = null
    @Expose
    @SerializedName("Success")
    var Success: Boolean = false

    class DataEntity {
        @Expose
        @SerializedName("TEXT")
        var TEXT: String? = null
        @Expose
        @SerializedName("VALUE")
        var VALUE: String? = null
    }
}

