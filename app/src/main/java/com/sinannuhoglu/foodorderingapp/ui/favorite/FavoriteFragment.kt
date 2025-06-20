package com.sinannuhoglu.foodorderingapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.navigation.fragment.findNavController
import com.sinannuhoglu.foodorderingapp.R
import com.sinannuhoglu.foodorderingapp.databinding.FragmentFavoriteBinding
import com.sinannuhoglu.foodorderingapp.ui.home.FoodAdapter
import com.sinannuhoglu.foodorderingapp.ui.home.FoodClickListener

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val favoritesViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var adapter: FoodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeFavorites() {
        favoritesViewModel.favorites.observe(viewLifecycleOwner) { list ->
            adapter = FoodAdapter(
                foodList = list,
                favoritesViewModel = favoritesViewModel,
                foodClickListener = object : FoodClickListener {
                    override fun onFoodClicked(foodId: String?) {
                        foodId?.let {
                            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
                            findNavController().navigate(action)
                        }
                    }
                }
            )
            binding.recyclerViewFavorites.adapter = adapter
        }
    }
}
