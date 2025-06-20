package com.sinannuhoglu.foodorderingapp.model


import com.google.gson.annotations.SerializedName

data class Yemekler(
    @SerializedName("yemek_adi")
    val yemekAdi: String?,
    @SerializedName("yemek_fiyat")
    val yemekFiyat: String?,
    @SerializedName("yemek_resim_adi")
    val yemekResimAdi: String?,
    @SerializedName("yemek_id")
    val yemekİd: String?
)