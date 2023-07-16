package com.example.sneakership.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sneakership.models.SneakersCart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {
    @Insert
    suspend fun insertCartItem(shoeCart: SneakersCart?)

    @Query("SELECT * FROM sneaker_table")
    fun getAllCartItems(): Flow<List<SneakersCart>>

    @Delete
    suspend fun deleteCartItem(shoeCart: SneakersCart?)
}
