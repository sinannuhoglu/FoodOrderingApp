package com.sinannuhoglu.foodorderingapp.model

data class BasketResponse(
    val sepet_yemekler: List<BasketItem>,
    val success: Int
)
