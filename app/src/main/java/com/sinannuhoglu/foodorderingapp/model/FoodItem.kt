package com.sinannuhoglu.foodorderingapp.model

data class FoodItem(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val price: Int,
    var isFavorite: Boolean = false
)
