package org.example.project.interfaz.Venta

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.dto.AsientoDTO
import org.example.project.estados.EstadoVenta
import org.example.project.proxy.ModeloVenta

@Composable
fun VentaInterfaz(
    eventoId: Long,
    seats: List<AsientoDTO>,
    precioUnitario: Double,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val viewModel = remember { ModeloVenta() }
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    var persona by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is EstadoVenta.Exitoso) {
            onSuccess()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.iniciarVenta(
            eventoId = eventoId,
            seats = seats,
            precioUnitario = precioUnitario,
        )
    }

    Scaffold(
        topBar = { HeaderVenta(onBack) },
        bottomBar = {
            if (uiState is EstadoVenta.Bloqueado) {
                FooterVenta(
                    total = viewModel.total(),
                    onConfirm = {
                        scope.launch {
                            viewModel.venderAsientos(
                                eventoId = eventoId,
                                precioTotal = viewModel.total(),
                                persona = persona,
                            )
                        }
                    },
                    enabled = persona.isNotBlank()
                )
            }
        },
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF121212), Color(0xFF242424))
                )
            )
    ) {
        Crossfade(targetState = uiState, modifier = Modifier.padding(it)) {
            when (it) {
                is EstadoVenta.Bloqueando, is EstadoVenta.Vendiendo -> {
                    LoadingView(if (it is EstadoVenta.Bloqueando) "Bloqueando asientos..." else "Procesando venta...")
                }
                is EstadoVenta.Error -> {
                    ErrorView((it as EstadoVenta.Error).message)
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        ListaAsientos(viewModel.asientosSeleccionados)

                        Spacer(Modifier.height(24.dp))

                        OutlinedTextField(
                            value = persona,
                            onValueChange = { persona = it },
                            label = { Text("Tus datos", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.White)
        Spacer(Modifier.height(16.dp))
        Text(message, color = Color.White)
    }
}

@Composable
private fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )
    }
}