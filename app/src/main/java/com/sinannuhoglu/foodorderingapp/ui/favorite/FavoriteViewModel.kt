package com.sinannuhoglu.foodorderingapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sinannuhoglu.foodorderingapp.model.Yemekler

class FavoriteViewModel : ViewModel() {

    private val _favorites = MutableLiveData<MutableList<Yemekler>>(mutableListOf())
    val favorites: LiveData<MutableList<Yemekler>> get() = _favorites

    fun toggleFavorite(food: Yemekler) {
        val currentList = _favorites.value ?: mutableListOf()
        val isAlreadyFavorite = currentList.any { it.yemekİd == food.yemekİd }

        if (isAlreadyFavorite) {
            currentList.removeAll { it.yemekİd == food.yemekİd }
        } else {
            currentList.add(food)
        }

        _favorites.value = currentList
    }

    fun isFavorite(foodId: String?): Boolean {
        return _favorites.value?.any { it.yemekİd == foodId } ?: false
    }
}
