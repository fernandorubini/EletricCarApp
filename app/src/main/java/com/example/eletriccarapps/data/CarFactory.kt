package com.example.eletriccarapps.data

import com.example.eletriccarapps.domain.Carro

object CarFactory {

    val list = listOf(
        Carro(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "300 kWh",
            potencia = "200cv",
            recarga = "30 min",
            urlPhoto = "https://example.com/car1.jpg", // Use a valid image URL
            isFavorite = false
        ),
        Carro(
            id = 2,
            preco = "R$ 200.000,00",
            bateria = "200 kWh",
            potencia = "150cv",
            recarga = "40 min",
            urlPhoto = "https://example.com/car2.jpg", // Use a valid image URL
            isFavorite = false
        )
    )
}