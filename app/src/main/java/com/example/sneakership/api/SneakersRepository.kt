package com.example.sneakership.api

import com.example.sneakership.models.Sneaker
import javax.inject.Inject

class SneakersRepository @Inject constructor(private val dataSource: SneakersDataSource) {

    suspend fun getSneakersData(): Resource<List<Sneaker>> {
        return dataSource.getSneakersData()
    }
}