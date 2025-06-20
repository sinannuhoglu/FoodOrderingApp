package com.sinannuhoglu.foodorderingapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sinannuhoglu.foodorderingapp.R
import com.sinannuhoglu.foodorderingapp.databinding.FragmentDetailBinding
import com.sinannuhoglu.foodorderingapp.model.Yemekler
import com.sinannuhoglu.foodorderingapp.ui.favorite.FavoriteViewModel
import com.sinannuhoglu.foodorderingapp.util.loadImage

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()
    private val favoritesViewModel: FavoriteViewModel by activityViewModels()
    private val args by navArgs<DetailFragmentArgs>()

    private var currentFood: Yemekler? = null
    private var quantity = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        observeViewModel()
        viewModel.getFoodDetail(args.foodId)
    }

    private fun setupListeners() {
        binding.imageViewClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageViewAdd.setOnClickListener {
            updateQuantity(quantity + 1)
        }

        binding.imageViewSubtract.setOnClickListener {
            if (quantity > 1) updateQuantity(quantity - 1)
        }

        binding.buttonAddToCart.setOnClickListener {
            currentFood?.let {
                val adet = binding.textViewQuantityValue.text.toString().toIntOrNull() ?: 1
                viewModel.addToCart(
                    yemekAdi = it.yemekAdi.orEmpty(),
                    yemekResimAdi = it.yemekResimAdi.orEmpty(),
                    yemekFiyat = it.yemekFiyat?.toIntOrNull() ?: 0,
                    yemekSiparisAdet = adet
                )
                Snackbar.make(binding.root, "Sepete eklendi", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.imageViewFavBorder.setOnClickListener {
            currentFood?.let {
                favoritesViewModel.toggleFavorite(it)
                updateFavoriteIcon()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar2.isVisible = it
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.textViewDetailError.text = it
            binding.textViewDetailError.isVisible = true
        }

        viewModel.foodResponse.observe(viewLifecycleOwner) { response ->
            val food = response.yemekler?.firstOrNull { it?.yemekİd == args.foodId }
            if (food != null) {
                currentFood = food
                bindFoodDetails(food)
            } else {
                binding.textViewDetailError.text = "Yemek bulunamadı."
                binding.textViewDetailError.isVisible = true
            }
        }
    }

    private fun bindFoodDetails(food: Yemekler) {
        with(binding) {
            imageViewDetail.loadImage(food.yemekResimAdi)
            textViewFoodName.text = food.yemekAdi
            textViewFoodPrice.text = food.yemekFiyat
        }
        quantity = 1
        updateQuantity(quantity)
        updateFavoriteIcon()
    }

    private fun updateQuantity(newQuantity: Int) {
        quantity = newQuantity
        val unitPrice = currentFood?.yemekFiyat?.toIntOrNull() ?: 0
        with(binding) {
            textViewQuantityValue.text = quantity.toString()
            textViewInfoValue.text = (quantity * unitPrice).toString()
        }
    }

    private fun updateFavoriteIcon() {
        val isFavorite = favoritesViewModel.isFavorite(currentFood?.yemekİd)
        val colorRes = if (isFavorite) R.color.red else R.color.gray
        binding.imageViewFavBorder.setColorFilter(
            ContextCompat.getColor(requireContext(), colorRes)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
