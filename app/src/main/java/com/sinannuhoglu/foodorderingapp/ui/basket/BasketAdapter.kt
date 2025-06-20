package com.sinannuhoglu.foodorderingapp.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinannuhoglu.foodorderingapp.databinding.ItemBasketBinding
import com.sinannuhoglu.foodorderingapp.model.BasketItem
import com.sinannuhoglu.foodorderingapp.util.loadImage

class BasketAdapter(
    private val items: List<BasketItem>,
    private val onDeleteClick: (BasketItem) -> Unit
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    inner class BasketViewHolder(val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            textViewName.text = item.yemek_adi
            textViewCount.text = "Adet: ${item.yemek_siparis_adet}"
            val adet = item.yemek_siparis_adet.toIntOrNull() ?: 1
            val fiyat = item.yemek_fiyat.toIntOrNull() ?: 0
            textViewPrice.text = "${adet * fiyat} ₺"
            imageViewFood.loadImage(item.yemek_resim_adi)
            buttonDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun getItemCount(): Int = items.size
}
