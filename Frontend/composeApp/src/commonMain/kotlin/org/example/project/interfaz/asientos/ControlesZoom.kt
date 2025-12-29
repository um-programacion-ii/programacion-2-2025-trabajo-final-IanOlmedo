package org.example.project.interfaz.asientos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ControlesZoom(
    zoomLevel: Float,
    onZoomChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { onZoomChange(zoomLevel - 0.2f) },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Zoom Out", tint = Color.White)
        }

        Text(
            "${(zoomLevel * 100).toInt()}%",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Medium,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        IconButton(
            onClick = { onZoomChange(zoomLevel + 0.2f) },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Zoom In", tint = Color.White)
        }
    }
}