package org.example.project.interfaz.asientos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.dto.AsientoDTO
import org.example.project.dto.MapaAsientosDTO

@Composable
fun MapaAsientosInterfaz(
    eventoId: Long,
    mapaAsientos: MapaAsientosDTO,
    onSeatsSelected: (List<AsientoDTO>) -> Unit,
    onBack: () -> Unit
) {
    var selectedSeats by remember { mutableStateOf<List<AsientoDTO>>(emptyList()) }
    var zoomLevel by remember { mutableStateOf(1f) }
    val maxSeats = 4

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val availableWidth = maxWidth

        val seatSize = remember(
            mapaAsientos.columnas,
            zoomLevel,
            maxWidth
        ) {
            val calculatedSize = (availableWidth / mapaAsientos.columnas) - 4.dp
            (calculatedSize.value.coerceIn(20f, 45f) * zoomLevel).dp
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF121212), Color(0xFF242424))
                    )
                )
        ) {
            HeaderMapaAsientos(
                disponibles = mapaAsientos.asientos.sumOf { fila -> fila.count { it.second == "DISPONIBLE" } },
                seleccionados = selectedSeats.size,
                maxSeats = maxSeats,
                onBack = onBack
            )

            LeyendaAsientos()

            ControlesZoom(
                zoomLevel = zoomLevel,
                onZoomChange = { newZoom ->
                    zoomLevel = newZoom.coerceIn(0.6f, 2f)
                }
            )

            IndicadorEscenario()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(mapaAsientos.asientos) { filaData ->
                        FilaAsientos(
                            filaData = filaData,
                            seatSize = seatSize,
                            selectedSeats = selectedSeats,
                            onSeatClick = { fila, columna, estado ->
                                if (estado != "DISPONIBLE") return@FilaAsientos

                                val seat = AsientoDTO(fila, columna)
                                val isSelected = selectedSeats.contains(seat)

                                selectedSeats = if (isSelected) {
                                    selectedSeats - seat
                                } else {
                                    if (selectedSeats.size < maxSeats) {
                                        selectedSeats + seat
                                    } else {
                                        selectedSeats
                                    }
                                }
                            }
                        )
                    }
                }
            }

            if (selectedSeats.isNotEmpty()) {
                PanelSeleccionAsientos(
                    selectedSeats = selectedSeats,
                    onConfirm = { onSeatsSelected(selectedSeats) }
                )
            }
        }
    }
}