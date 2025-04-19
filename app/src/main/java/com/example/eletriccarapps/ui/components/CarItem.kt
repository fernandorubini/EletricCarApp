package com.example.eletriccarapps.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.eletriccarapps.domain.Carro
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder

@Composable
fun CarItem(
    car: Carro,
    onFavoriteClick: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(car.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(car.urlPhoto),
                contentDescription = "Imagem do carro",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "ID: ${car.id}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Preço: ${car.preco}")
                Text(text = "Bateria: ${car.bateria}")
                Text(text = "Potência: ${car.potencia}")
                Text(text = "Recarga: ${car.recarga}")
            }

            IconButton(onClick = {
                isFavorite = !isFavorite
                onFavoriteClick()
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (isFavorite) "Remover dos favoritos" else "Adicionar aos favoritos",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
