package com.example.sneakership.viewModels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sneakership.R
import com.example.sneakership.api.*
import com.example.sneakership.helper.*
import com.example.sneakership.models.Sneaker
import com.example.sneakership.models.SneakersCart
import com.example.sneakership.repository.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SneakersListViewModel @Inject constructor(
    private val sneakersRepository: SneakersRepository, private val cartRepo: CartRepo
) : ViewModel() {

    val sneakerList = MutableLiveData<List<Sneaker>>()
    val errorText = ObservableField<String>()
    val filteredSneakers = MutableLiveData<List<Sneaker>>()
    val showError = ObservableBoolean()
    val showProgressBar = ObservableBoolean()
    var currentSortOrder = MutableLiveData(SortOrder.DEFAULT)

    fun fetchSneakers() {
        showProgressBar.set(true)
        viewModelScope.launch {
            try {
                when (val response = sneakersRepository.getSneakersData()) {
                    is Resource.Success -> sneakerList.value = response.data
                    is Resource.Error -> setError(response.message)
                }
                showProgressBar.set(false)
            } catch (e: Exception) {
                showProgressBar.set(false)
            }
        }
    }

    private fun setError(message: String?) {
        showError.set(true)
        errorText.set(message)
        showProgressBar.set(false)
    }

    private fun disableError() {
        showError.set(false)
        showProgressBar.set(true)
    }

    fun filterSneakers(query: String) {
        disableError()
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val lowercaseQuery = query.lowercase(Locale.getDefault())
                val filteredList = sneakerList.value?.filter { sneaker ->
                    sneaker.brand.lowercase(Locale.getDefault())
                        .contains(lowercaseQuery) || sneaker.name.lowercase(Locale.getDefault())
                        .contains(lowercaseQuery)
                }
                withContext(Dispatchers.Main) {
                    filteredSneakers.value = filteredList
                    if (filteredList?.isEmpty() == true) {
                        setError(getString(R.string.no_sneakers_found))
                    }
                    showProgressBar.set(false)
                }
            }
        }
    }

    fun sortSneakers(sortOrder: SortOrder) {
        showProgressBar.set(true)
        viewModelScope.launch {
            val sortedList = withContext(Dispatchers.Default) {
                when (sortOrder) {
                    SortOrder.DEFAULT -> sneakerList.value
                    SortOrder.PRICE_LOW_TO_HIGH -> sneakerList.value?.sortedBy { it.retailPrice }
                    SortOrder.PRICE_HIGH_TO_LOW -> sneakerList.value?.sortedByDescending { it.retailPrice }
                }
            }
            withContext(Dispatchers.Main) {
                currentSortOrder.value = sortOrder
                filteredSneakers.value = sortedList
                showProgressBar.set(false)
            }
        }
    }

    fun addSneakerToCart(sneakersCart: SneakersCart) {
        viewModelScope.launch {
            cartRepo.insertCartItem(sneakersCart)
            showShortToast(sneakersCart.name + SPACE_FILLER + getString(R.string.added_to_cart))
        }
    }
}
