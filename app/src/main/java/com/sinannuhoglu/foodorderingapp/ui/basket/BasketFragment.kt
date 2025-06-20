
package com.sinannuhoglu.foodorderingapp.ui.basket

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sinannuhoglu.foodorderingapp.R
import com.sinannuhoglu.foodorderingapp.databinding.FragmentBasketBinding
import com.sinannuhoglu.foodorderingapp.model.BasketItem

class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BasketViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.fetchBasket()
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.basketItems.observe(viewLifecycleOwner) { items ->
            binding.basketList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = BasketAdapter(items) { selectedItem -> showDeleteDialog(selectedItem) }
            }

            val total = items.sumOf {
                (it.yemek_fiyat.toIntOrNull() ?: 0) * (it.yemek_siparis_adet.toIntOrNull() ?: 1)
            }
            binding.textViewInfoValue.text = total.toString()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it ?: "Bilinmeyen hata", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {
        binding.buttonPlaceOrder.setOnClickListener {
            Snackbar.make(requireView(), "Siparişiniz alındı.", Snackbar.LENGTH_SHORT)
                .setAnchorView(requireActivity().findViewById(R.id.bottomNavigation))
                .show()
        }
    }

    private fun showDeleteDialog(item: BasketItem) {
        AlertDialog.Builder(requireContext())
            .setTitle("Yemek Sil")
            .setMessage("${item.yemek_adi} adlı ürünü silmek istediğinize emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                viewModel.deleteItem(item.sepet_yemek_id.toInt(), "sinan_nuhoglu") { success ->
                    val message = if (success) "${item.yemek_adi} silindi" else "Silme işlemi başarısız"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("İptal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
