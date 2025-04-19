package com.example.eletriccarapps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.eletriccarapps.data.local.CarLocalRepository
import com.example.eletriccarapps.data.repository.CarRemoteRepository
import com.example.eletriccarapps.domain.Carro
import com.example.eletriccarapps.ui.components.CarItem
import com.example.eletriccarapps.utils.checkForInternet

@Composable
fun CarScreen() {
    val context = LocalContext.current
    val remoteRepository = remember { CarRemoteRepository() }
    val localRepository = remember { CarLocalRepository(context) }

    var carList by remember { mutableStateOf<List<Carro>>(emptyList()) }
    var hasInternet by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (checkForInternet(context)) {
            try {
                val remoteCars = remoteRepository.fetchCarData()
                remoteCars.forEach { car ->
                    localRepository.saveIfNotExist(car)
                }
                carList = remoteCars
            } catch (e: Exception) {
                errorMessage = "Erro ao buscar dados: ${e.message}"
            }
        } else {
            hasInternet = false
            carList = localRepository.getAll()
        }
    }


    when {
        !hasInternet -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sem conexão com a internet")
            }
        }

        errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage ?: "Erro desconhecido")
            }
        }

        carList.isEmpty() -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        else -> {
            LazyColumn(Modifier.fillMaxSize()) {
                items(carList) { car ->
                    CarItem(
                        car = car,
                        onFavoriteClick = {
                            car.isFavorite = !car.isFavorite
                            if (car.isFavorite) {
                                localRepository.save(car)
                            } else {
                                localRepository.delete(car)
                            }
                        }
                    )
                }
            }
        }
    }
}
