package com.example.sneakership.viewModels

import androidx.databinding.ObservableField

class SneakerItemViewModel(
    name: String?, val imageUrl: String?, retailPrice: Double?
) {
    var sneakerName = ObservableField<String>()
    var sneakerPrice = ObservableField<String>()

    init {
        sneakerName.set(name)
        sneakerPrice.set("$ $retailPrice")
    }
}