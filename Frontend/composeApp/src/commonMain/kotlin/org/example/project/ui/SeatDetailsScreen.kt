package org.example.project.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.content.venta.SeatDetailsScreenContent
import org.example.project.dto.AsientoSeleccionado

data class SeatDetailsScreen(
    val eventoId: Long,
    val seats: List<AsientoSeleccionado>,
    val precioUnitario: Double
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SeatDetailsScreenContent(
            eventoId = eventoId,
            seats = seats,
            onBack = { navigator.pop() },
            onSuccess = { navigator.pop() },
            precioUnitario = precioUnitario
        )
    }
}