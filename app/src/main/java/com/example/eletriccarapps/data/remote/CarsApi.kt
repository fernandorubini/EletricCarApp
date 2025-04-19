package com.example.eletriccarapps.data.remote

import com.example.eletriccarapps.domain.Carro
import retrofit2.http.GET

interface CarsApi {
    @GET("cars.json")
    suspend fun getAllCars(): List<Carro>
}
