package com.example.eletriccarapps.data.repository

import com.example.eletriccarapps.domain.Carro

class CarRemoteRepository {

    suspend fun fetchCarData(): List<Carro> {
        return listOf(
            Carro(
                id = 1,
                preco = "R$ 480.000",
                bateria = "100 kWh",
                potencia = "1020 cv",
                recarga = "30 min",
                urlPhoto = "https://imagem1.com",
                isFavorite = false
            ),
            Carro(
                id = 2,
                preco = "R$ 260.000",
                bateria = "42 kWh",
                potencia = "170 cv",
                recarga = "45 min",
                urlPhoto = "https://imagem2.com",
                isFavorite = true
            )
        )
    }
}
