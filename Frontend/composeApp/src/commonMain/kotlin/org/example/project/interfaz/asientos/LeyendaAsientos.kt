package org.example.project.interfaz.asientos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LeyendaAsientos() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LeyendaItem("Disponible", Color(0xFF4CAF50))
        LeyendaItem("Seleccionado", Color(0xFF007AFF))
        LeyendaItem("Bloqueado", Color.Gray)
        LeyendaItem("Vendido", Color(0xFFE53935))
    }
}