package com.example.sneakership.api

import com.example.sneakership.models.Sneaker
import javax.inject.Inject

class SneakersDataSource @Inject constructor(
    private val sneakersService: SneakersService, private val errorHandler: ErrorHandler
) {

    suspend fun getSneakersData(): Resource<List<Sneaker>> {
        return errorHandler.handleApiResponse(sneakersService.getSneakersData())
    }
}
