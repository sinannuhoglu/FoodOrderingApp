package com.sinannuhoglu.foodorderingapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sinannuhoglu.foodorderingapp.databinding.FragmentHomeBinding
import com.sinannuhoglu.foodorderingapp.model.Yemekler
import com.sinannuhoglu.foodorderingapp.ui.favorite.FavoriteViewModel
import com.sinannuhoglu.foodorderingapp.util.GridSpacingItemDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val favoritesViewModel by activityViewModels<FavoriteViewModel>()

    private var fullList: List<Yemekler> = listOf()
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        setupSearchBar()
        return binding.root
    }

    private fun setupRecyclerView() {
        val spacing = resources.getDimensionPixelSize(com.sinannuhoglu.foodorderingapp.R.dimen.grid_spacing)
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.homeRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.textViewHomeError.apply {
                text = it ?: "Beklenmeyen bir hata oluştu."
                isVisible = true
            }
        }

        viewModel.foodList.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.textViewHomeError.text = "Yemek bulunamadı."
                binding.textViewHomeError.isVisible = true
            } else {
                fullList = list as List<Yemekler>
                updateAdapter(fullList)
            }
        }
    }

    private fun setupSearchBar() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.lowercase()?.trim() ?: ""
                val filteredList = fullList.filter {
                    it.yemekAdi?.lowercase()?.contains(query) == true
                }
                updateAdapter(filteredList)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateAdapter(list: List<Yemekler>) {
        foodAdapter = FoodAdapter(list, object : FoodClickListener {
            override fun onFoodClicked(foodId: String?) {
                foodId?.let {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                    findNavController().navigate(action)
                }
            }
        }, favoritesViewModel)

        binding.homeRecyclerView.adapter = foodAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
