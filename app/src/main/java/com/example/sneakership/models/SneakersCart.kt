package com.example.sneakership.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sneaker_table")
data class SneakersCart(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String? = null,
    var image: String? = null,
    var price: Double = 0.0,
)
