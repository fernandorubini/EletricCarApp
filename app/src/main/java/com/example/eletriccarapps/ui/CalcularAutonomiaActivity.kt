package com.example.eletriccarapps.ui.screens

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.eletriccarapps.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Extensão do Context para usar DataStore
val Context.dataStore by preferencesDataStore("autonomia_calc")
val RESULT_KEY = floatPreferencesKey("saved_calc")

class CalcularAutonomiaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcularAutonomiaScreen(onFinish = { finish() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcularAutonomiaScreen(onFinish: () -> Unit) {
    val context = LocalContext.current
    var kmPercorrido by remember { mutableStateOf("") }
    var precoKwh by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Carrega resultado salvo
    LaunchedEffect(Unit) {
        val savedResult = loadSavedResult(context)
        resultado = String.format("%.2f", savedResult)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.calcular_autonomia)) },
                navigationIcon = {
                    IconButton(onClick = onFinish) {
                        Icon(Icons.Filled.Close, contentDescription = "Fechar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = precoKwh,
                onValueChange = { precoKwh = it },
                label = { Text(stringResource(R.string.preco_kwh)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = kmPercorrido,
                onValueChange = { kmPercorrido = it },
                label = { Text(stringResource(R.string.km_percorrido)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Button(
                onClick = {
                    val preco = precoKwh.toFloatOrNull() ?: 0f
                    val km = kmPercorrido.toFloatOrNull() ?: 0f
                    val result = if (km != 0f) preco / km else 0f
                    resultado = String.format("%.2f", result)

                    coroutineScope.launch {
                        saveResult(context, result)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(stringResource(R.string.calcular))
            }

            Text(
                text = stringResource(R.string.resultado, resultado),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

// Função para salvar o resultado
suspend fun saveResult(context: Context, result: Float) {
    context.dataStore.edit { preferences ->
        preferences[RESULT_KEY] = result
    }
}

// Função para recuperar o resultado salvo
suspend fun loadSavedResult(context: Context): Float {
    val preferences = context.dataStore.data.first()
    return preferences[RESULT_KEY] ?: 0f
}
