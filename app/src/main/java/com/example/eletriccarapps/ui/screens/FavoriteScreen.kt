package com.example.eletriccarapps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.eletriccarapps.data.local.CarLocalRepository
import com.example.eletriccarapps.domain.Carro
import com.example.eletriccarapps.ui.components.CarItem

@Composable
fun FavoriteScreen() {
    val context = LocalContext.current
    val carRepository = CarLocalRepository(context)
    var favoriteCars by remember { mutableStateOf(emptyList<Carro>()) }

    LaunchedEffect(Unit) {
        favoriteCars = carRepository.getAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Carros Favoritos") })
        }
    ) { paddingValues ->
        FavoriteCarList(
            favoriteCars = favoriteCars,
            paddingValues = paddingValues,
            onRemoveFavorite = { car ->
                carRepository.delete(car)
                favoriteCars = carRepository.getAll() // Atualiza a lista após remover
            }
        )
    }
}

@Composable
fun FavoriteCarList(
    favoriteCars: List<Carro>,
    paddingValues: PaddingValues,
    onRemoveFavorite: (Carro) -> Unit
) {
    if (favoriteCars.isEmpty()) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Nenhum carro favorito ainda!")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(favoriteCars) { car ->
                CarItem(car = car, onFavoriteClick = { onRemoveFavorite(car) })
            }
        }
    }
}
