package com.example.sneakership.api

import com.example.sneakership.models.Sneaker
import retrofit2.Response
import retrofit2.http.GET

interface SneakersService {
    @GET(SNEAKERS_LIST_URL)
    suspend fun getSneakersData(): Response<List<Sneaker>>
}