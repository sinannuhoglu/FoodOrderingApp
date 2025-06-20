package com.sinannuhoglu.foodorderingapp.model


import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("success")
    val success: Int?,
    @SerializedName("yemekler")
    val yemekler: List<Yemekler?>?
)