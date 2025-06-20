package com.sinannuhoglu.foodorderingapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinannuhoglu.foodorderingapp.model.Yemekler
import com.sinannuhoglu.foodorderingapp.network.ApiClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val foodList = MutableLiveData<List<Yemekler>>()
    val isLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()

    init {
        fetchFoodList()
    }

    private fun fetchFoodList() {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getFoodList()
                if (response.isSuccessful) {
                    val data = response.body()?.yemekler?.filterNotNull().orEmpty()
                    foodList.postValue(data)
                } else {
                    errorMessage.postValue("Sunucu hatası: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Ağ hatası: ${e.localizedMessage}")
            } finally {
                isLoading.value = false
            }
        }
    }
}
