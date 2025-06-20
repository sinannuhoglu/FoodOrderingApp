package com.sinannuhoglu.foodorderingapp.network

import com.sinannuhoglu.foodorderingapp.model.BasketResponse
import com.sinannuhoglu.foodorderingapp.model.FoodResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("tumYemekleriGetir.php")
    suspend fun getFoodList(): Response<FoodResponse>

    @FormUrlEncoded
    @POST("sepeteYemekEkle.php")
    suspend fun addToCart(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekSiparisAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("sepettekiYemekleriGetir.php")
    suspend fun getBasketItems(
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<BasketResponse>

    @FormUrlEncoded
    @POST("sepettenYemekSil.php")
    suspend fun deleteFromBasket(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<Map<String, Any>>
}