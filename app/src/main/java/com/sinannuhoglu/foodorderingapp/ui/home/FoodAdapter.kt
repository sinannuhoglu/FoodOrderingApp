package com.sinannuhoglu.foodorderingapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sinannuhoglu.foodorderingapp.R
import com.sinannuhoglu.foodorderingapp.databinding.ItemHomeRecyclerViewBinding
import com.sinannuhoglu.foodorderingapp.model.Yemekler
import com.sinannuhoglu.foodorderingapp.ui.favorite.FavoriteViewModel
import com.sinannuhoglu.foodorderingapp.util.loadCircleImage

interface FoodClickListener {
    fun onFoodClicked(foodId: String?)
}

class FoodAdapter(
    private val foodList: List<Yemekler>,
    private val foodClickListener: FoodClickListener,
    private val favoritesViewModel: FavoriteViewModel
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHomeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeRecyclerViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]

        with(holder.binding) {
            textViewFoodName.text = food.yemekAdi
            textViewFoodFee.text = food.yemekFiyat
            imageViewFood.loadCircleImage(food.yemekResimAdi)

            val isFavorite = favoritesViewModel.isFavorite(food.yemekİd)
            val iconColor = if (isFavorite) R.color.red else R.color.gray
            imageViewFavBorder.setColorFilter(ContextCompat.getColor(root.context, iconColor))

            imageViewFavBorder.setOnClickListener {
                favoritesViewModel.toggleFavorite(food)
                notifyItemChanged(position)
            }

            root.setOnClickListener {
                foodClickListener.onFoodClicked(food.yemekİd)
            }
        }
    }
}
