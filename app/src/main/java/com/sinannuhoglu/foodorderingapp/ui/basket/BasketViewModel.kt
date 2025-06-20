package com.sinannuhoglu.foodorderingapp.ui.basket

import androidx.lifecycle.*
import com.sinannuhoglu.foodorderingapp.model.BasketItem
import com.sinannuhoglu.foodorderingapp.network.ApiClient
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {

    val basketItems = MutableLiveData<List<BasketItem>>()
    val errorMessage = MutableLiveData<String?>()

    fun fetchBasket(kullaniciAdi: String = "sinannuhoglu") {
        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getBasketItems(kullaniciAdi)
                if (response.isSuccessful) {
                    basketItems.postValue(response.body()?.sepet_yemekler ?: emptyList())
                } else {
                    errorMessage.postValue("Hata: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("İnternet hatası: ${e.localizedMessage}")
            }
        }
    }

    fun deleteItem(sepetYemekId: Int, kullaniciAdi: String = "sinannuhoglu", onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().deleteFromBasket(sepetYemekId, kullaniciAdi)
                if (response.isSuccessful && response.body()?.get("success") == 1.0) {
                    onResult(true)
                    fetchBasket(kullaniciAdi)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}
