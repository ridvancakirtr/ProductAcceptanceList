package com.example.myapplication.Responce

import com.example.myapplication.Model.*
import retrofit2.Call
import retrofit2.http.*

interface Responce {

    @POST("/WebApi/Baglanti")
    fun ConnectionControl(): Call<ConnectionControl>

    @FormUrlEncoded
    @POST("/webapi/KullaniciToken")
    fun getUserInformation(@Field("username") username:String, @Field("password") password:String): Call<UserInformation>

    @FormUrlEncoded
    @POST("/webapi/CariAlisListesi")
    fun getCurrentValues(@Field("kullanici_id") user_id:String, @Field("token") token:String): Call<Current>

    @FormUrlEncoded
    @POST("/webapi/MalKabulListesi")
    fun getMalKabul(@Field("kullanici_id") user_id:String, @Field("token") token:String, @Field("cari_kod") cari_kod:String): Call<ItemAccept>

    @POST("/webapi/MalKabulListesiKaydet")
    fun postSeries(@Body seriespost:SeriesPostDataModel): Call<SeriesRespondDataModel>
}