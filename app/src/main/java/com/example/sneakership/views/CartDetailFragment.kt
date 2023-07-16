package com.example.sneakership.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakership.R
import com.example.sneakership.adapters.CartDetailAdapter
import com.example.sneakership.databinding.FragmentCartDetailBinding
import com.example.sneakership.helper.showShortToast
import com.example.sneakership.viewModels.CartDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartDetailFragment : Fragment() {

    private lateinit var binding: FragmentCartDetailBinding
    private lateinit var cartDetailAdapter: CartDetailAdapter

    @Inject
    lateinit var cartDetailViewModel: CartDetailViewModel

    companion object {
        fun newInstance() = CartDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartDetailBinding.inflate(inflater)
        binding.viewModel = cartDetailViewModel
        cartDetailAdapter = CartDetailAdapter(cartDetailViewModel)

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.VERTICAL }
            adapter = cartDetailAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpObservers()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.checkoutButton.setOnClickListener {
            val message =
                if (cartDetailViewModel.cartItems.value.isNullOrEmpty()) R.string.add_items_to_check_out else R.string.order_placed
            showShortToast(getString(message))
        }
        binding.backButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun setUpObservers() {
        cartDetailViewModel.cartItems.observe(this) {
            cartDetailAdapter.submitList(it)
            lifecycleScope.launch {
                cartDetailViewModel.calculateTotalValueWithTax()
            }
        }
    }
}