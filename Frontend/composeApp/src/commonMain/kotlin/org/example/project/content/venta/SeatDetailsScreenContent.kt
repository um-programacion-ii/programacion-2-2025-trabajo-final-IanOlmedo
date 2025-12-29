package org.example.project.content.venta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.project.dto.AsientoSeleccionado
import org.example.project.viewmodel.SeatDetailsUiState
import org.example.project.viewmodel.SeatDetailsViewModel


@Composable
fun SeatDetailsScreenContent(
    eventoId: Long,
    seats: List<AsientoSeleccionado>,
    precioUnitario: Double,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val viewModel = remember { SeatDetailsViewModel() }
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    // Navegar cuando la venta fue exitosa
    LaunchedEffect(uiState) {
        if (uiState is SeatDetailsUiState.Success) {
            onSuccess()
        }
    }

    // 🔒 Bloqueo al entrar
    LaunchedEffect(Unit) {
        viewModel.iniciarVenta(
            eventoId = eventoId,
            seats = seats,
            precioUnitario = precioUnitario, // o viene del evento
        )
    }

    when (uiState) {
        SeatDetailsUiState.Blocking -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
                Text("Bloqueando asientos...")
            }
        }

        SeatDetailsUiState.Selling -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SeatDetailsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (uiState as SeatDetailsUiState.Error).message,
                    color = Color.Red
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SeatDetailsHeader(onBack)

                Spacer(Modifier.height(16.dp))

                SeatDetailsList(viewModel.asientosSeleccionados)

                Spacer(Modifier.height(16.dp))

                Text(
                    "Total: $${viewModel.total()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.venderAsientos(
                                eventoId = eventoId,
                                precioTotal = viewModel.total(),
                                persona = "admin",
                            )
                        }
                    },
                    enabled = uiState is SeatDetailsUiState.Blocked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar compra")
                }
            }
        }
    }
}
