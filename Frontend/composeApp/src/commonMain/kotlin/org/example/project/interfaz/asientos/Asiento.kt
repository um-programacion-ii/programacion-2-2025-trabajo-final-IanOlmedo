package org.example.project.interfaz.asientos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Asiento(
    fila: Int,
    asiento: Pair<Int, String>,
    size: Dp,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = when {
        isSelected -> Color(0xFF007AFF)
        asiento.second == "DISPONIBLE" -> Color(0xFF4CAF50)
        asiento.second == "BLOQUEADO" -> Color.Gray
        asiento.second == "VENDIDO" -> Color(0xFFE53935)
        else -> Color.DarkGray
    }

    val enabled = asiento.second == "DISPONIBLE"

    Box(
        modifier = Modifier
            .size(size)
            .background(color.copy(alpha = if (enabled) 0.3f else 0.1f), RoundedCornerShape(4.dp))
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (size > 25.dp) {
            Text(
                text = "${asiento.first}",
                fontSize = (size.value * 0.3f).sp,
                color = if (isSelected || !enabled) Color.White else color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}