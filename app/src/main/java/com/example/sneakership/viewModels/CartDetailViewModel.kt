package com.example.sneakership.viewModels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakership.R
import com.example.sneakership.helper.SPACE_FILLER
import com.example.sneakership.helper.getString
import com.example.sneakership.helper.showShortToast
import com.example.sneakership.models.SneakersCart
import com.example.sneakership.repository.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartDetailViewModel @Inject constructor(val cartRepo: CartRepo) : ViewModel() {
    val cartItems = MutableLiveData<List<SneakersCart>>()
    val subTotal = ObservableField<String>()
    val taxes = ObservableField<String>()
    val totalValue = ObservableField<String>()
    private val taxPercentage = 18

    init {
        getAllCartItems()
    }

    private fun getAllCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.getAllCartItems().collect {
                withContext(Dispatchers.Main) {
                    cartItems.value = it
                }
            }
        }
    }

    fun removeSneakerFromCart(sneaker: SneakersCart) {
        viewModelScope.launch {
            cartRepo.deleteCartItem(sneaker)
            showShortToast(sneaker.name + SPACE_FILLER + getString(R.string.removed_from_cart))
        }
    }

    suspend fun calculateSubTotalValue(): Double {
        return withContext(Dispatchers.Default) {
            val items = cartItems.value ?: emptyList()
            items.sumOf { it.price }
        }
    }

    suspend fun calculateTotalValueWithTax() {
        val total = calculateSubTotalValue()
        val tax = total * taxPercentage / 100
        subTotal.set(getString(R.string.sub_total) + total.toString())
        taxes.set(getString(R.string.taxes_and_charges) + tax.toString())
        totalValue.set("$" + (total + tax).toString())
    }
}