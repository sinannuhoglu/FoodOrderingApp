package com.sinannuhoglu.foodorderingapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinannuhoglu.foodorderingapp.model.FoodResponse
import com.sinannuhoglu.foodorderingapp.network.ApiClient
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val foodResponse = MutableLiveData<FoodResponse>()
    val isLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()

    fun getFoodDetail(foodId: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getFoodList()
                if (response.isSuccessful) {
                    foodResponse.postValue(response.body())
                } else {
                    errorMessage.value = response.message().ifEmpty { "Bilinmeyen bir hata oluştu" }
                }
            } catch (e: Exception) {
                errorMessage.value = "İnternet hatası: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int,
        kullaniciAdi: String = "sinannuhoglu"
    ) {
        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().addToCart(
                    yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi
                )
                if (!response.isSuccessful) {
                    errorMessage.postValue("Sepete eklenemedi: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Hata: ${e.localizedMessage}")
            }
        }
    }
}
