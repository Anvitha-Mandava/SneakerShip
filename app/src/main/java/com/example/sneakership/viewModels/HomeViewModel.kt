package com.example.sneakership.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakership.R
import com.example.sneakership.helper.FragmentType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var selectedFragment = MutableLiveData<FragmentType>()

    init {
        selectedFragment.value = FragmentType.HOME
    }

    fun onNavigationItemSelected(itemId: Int): Boolean {
        val fragmentType = when (itemId) {
            R.id.home -> FragmentType.HOME
            R.id.cart -> FragmentType.CART
            else -> return false
        }
        if (selectedFragment.value != fragmentType) {
            selectedFragment.value = fragmentType
        }
        return true
    }
}
