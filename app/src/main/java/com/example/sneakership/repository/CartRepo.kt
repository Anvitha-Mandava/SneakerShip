package com.example.sneakership.repository

import android.content.Context
import com.example.sneakership.dao.CartDAO
import com.example.sneakership.database.CartDatabase
import com.example.sneakership.models.SneakersCart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllCartItems(): Flow<List<SneakersCart>>
    suspend fun insertCartItem(sneakersCart: SneakersCart?)
    suspend fun deleteCartItem(sneakersCart: SneakersCart?)
}

class CartRepo(context: Context) : CartRepository {

    private var cartDAO: CartDAO

    init {
        cartDAO = CartDatabase.getInstance(context).cartDAO()
    }

    override fun getAllCartItems(): Flow<List<SneakersCart>> {
        return cartDAO.getAllCartItems()
    }

    override suspend fun insertCartItem(sneakersCart: SneakersCart?) {
        cartDAO.insertCartItem(sneakersCart)
    }

    override suspend fun deleteCartItem(sneakersCart: SneakersCart?) {
        cartDAO.deleteCartItem(sneakersCart)
    }
}
