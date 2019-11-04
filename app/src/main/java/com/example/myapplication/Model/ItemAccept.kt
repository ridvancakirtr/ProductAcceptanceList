package com.example.myapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemAccept {

    @Expose
    @SerializedName("Data")
    val mData: List<DataEntity>? = null
    @Expose
    @SerializedName("Success")
    val mSuccess: Boolean = false

    class DataEntity {
        @Expose
        @SerializedName("GEN_ISK1O")
        val mGEN_ISK1O: Int = 0
        @Expose
        @SerializedName("STHAR_SATISK2")
        val mSTHAR_SATISK2: Int = 0
        @Expose
        @SerializedName("STRA_SATISK6")
        val mSTRA_SATISK6: Int = 0
        @Expose
        @SerializedName("STRA_SATISK5")
        val mSTRA_SATISK5: Int = 0
        @Expose
        @SerializedName("STRA_SATISK4")
        val mSTRA_SATISK4: Int = 0
        @Expose
        @SerializedName("STRA_SATISK3")
        val mSTRA_SATISK3: Int = 0
        @Expose
        @SerializedName("GIRIS_SERI")
        val mGIRIS_SERI: String? = null
        @Expose
        @SerializedName("CARI_KODU")
        val mCARI_KODU: String? = null
        @Expose
        @SerializedName("STHAR_FTIRSIP")
        val mSTHAR_FTIRSIP: String? = null
        @Expose
        @SerializedName("FISNO")
        val mFISNO: String? = null
        @Expose
        @SerializedName("SIPARIS_TEST")
        val mSIPARIS_TEST: String? = null
        @Expose
        @SerializedName("TARIH")
        val mTARIH: String? = null
        @Expose
        @SerializedName("DURUM")
        val mDURUM: String? = null
        @Expose
        @SerializedName("STHAR_SATISK")
        val mSTHAR_SATISK: Double = 0.0
        @Expose
        @SerializedName("STHAR_KDV")
        val mSTHAR_KDV: Int = 0
        @Expose
        @SerializedName("STHAR_BF")
        val mSTHAR_BF: Double = 0.0
        @Expose
        @SerializedName("STHAR_NF")
        val mSTHAR_NF: Double = 0.0
        @Expose
        @SerializedName("TESLIM_MIK")
        val mTESLIM_MIK: Double = 0.0
        @Expose
        @SerializedName("SIPARIS_MIK")
        val mSIPARIS_MIK: Int = 0
        @Expose
        @SerializedName("STOK_ADI")
        val mSTOK_ADI: String? = null
        @Expose
        @SerializedName("STOK_KODU")
        val mSTOK_KODU: String? = null
        @Expose
        @SerializedName("INCKEYNO")
        val mINCKEYNO: Int = 0
    }
}
