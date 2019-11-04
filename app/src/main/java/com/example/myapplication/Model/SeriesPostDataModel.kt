package com.example.myapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeriesPostDataModel(
    @field:Expose
    @field:SerializedName("mk_kaydet")
    var mk_kaydet: Mk_kaydetEntity?, @field:Expose
    @field:SerializedName("token")
    var token: String?, @field:Expose
    @field:SerializedName("kullanici_id")
    var kullanici_id: String?
) {

    class Mk_kaydetEntity(
        @field:Expose
        @field:SerializedName("MALKABULLISTESI")
        var malkabullistesi: ArrayList<MALKABULLISTESIEntity>?, @field:Expose
        @field:SerializedName("BELGE_NO")
        var belgE_NO: String?, @field:Expose
        @field:SerializedName("BELGE_TARIH")
        var belgE_TARIH: String?, @field:Expose
        @field:SerializedName("CARI_KODU")
        var carI_KODU: String?
    )

    class MALKABULLISTESIEntity(
        @field:Expose
        @field:SerializedName("SERI_LIST")
        var serI_LIST: ArrayList<SERI_LISTEntity>?,
        @field:Expose
        @field:SerializedName("TESLIMMIK")
        var teslimmik: Int, @field:Expose
        @field:SerializedName("INCKEYNO")
        var inckeyno: Int
    )

    class SERI_LISTEntity(
        @field:Expose
        @field:SerializedName("MIKTAR")
        var miktar: Int, @field:Expose
        @field:SerializedName("SERI")
        var seri: String?
    )
}
