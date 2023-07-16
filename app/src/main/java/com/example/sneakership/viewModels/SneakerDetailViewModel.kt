package com.example.sneakership.viewModels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakership.R
import com.example.sneakership.helper.*
import com.example.sneakership.models.Sneaker
import com.example.sneakership.models.SneakersCart
import com.example.sneakership.repository.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SneakerDetailViewModel @Inject constructor(private val cartRepo: CartRepo) : ViewModel() {

    var currentImageUrl = ObservableField<String>()
    var imageUrlSet = ArrayList<String?>()
    var sneaker: Sneaker? = null
    var sneakerName = ObservableField<String>()
    var sneakerDescription = ObservableField<String>()
    var sneakerPrice = ObservableField<String>()
    private var currentIndex = 0

    fun loadSneakerData() {
        setImageUrls()
        sneakerName.set(sneaker?.name)
        sneakerDescription.set(sneaker?.title + SPACE_FILLER + sneaker?.gender)
        sneakerPrice.set("$" + sneaker?.retailPrice)
        setCurrentImageUrl()
    }

    fun showNextImage() {
        currentIndex = (currentIndex + 1) % imageUrlSet.size
        setCurrentImageUrl()
    }

    fun showPreviousImage() {
        currentIndex = (currentIndex - 1 + imageUrlSet.size) % imageUrlSet.size
        setCurrentImageUrl()
    }

    private fun setCurrentImageUrl() {
        currentImageUrl.set(imageUrlSet[currentIndex])
    }

    private fun setImageUrls() {
        imageUrlSet = arrayListOf(
            sneaker?.media?.imageUrl, sneaker?.media?.smallImageUrl, sneaker?.media?.thumbUrl
        )
    }

    fun addSneakerToCart() {
        viewModelScope.launch {
            cartRepo.insertCartItem(
                SneakersCart(
                    name = sneaker?.name,
                    image = sneaker?.media?.imageUrl,
                    price = sneaker?.retailPrice.orZero()
                )
            )
            showShortToast(sneaker?.name + SPACE_FILLER + getString(R.string.added_to_cart))
        }
    }
}
