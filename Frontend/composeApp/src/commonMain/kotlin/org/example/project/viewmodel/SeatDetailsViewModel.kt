package org.example.project.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.dto.AsientoSeleccionado
import org.example.project.dto.AsientosComprasDTO
import org.example.project.dto.AsientosVentasDTO
import org.example.project.dto.VentaAsientosRequest
import org.example.project.network.ApiClient
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SeatDetailsViewModel {

    private val _uiState = MutableStateFlow<SeatDetailsUiState>(SeatDetailsUiState.Idle)
    val uiState: StateFlow<SeatDetailsUiState> = _uiState.asStateFlow()
    private var precioUnitario: Double = 0.0
    private var _asientosSeleccionados: List<AsientoSeleccionado> = emptyList()
    val asientosSeleccionados: List<AsientoSeleccionado>
        get() = _asientosSeleccionados
    suspend fun iniciarVenta(
        eventoId: Long,
        seats: List<AsientoSeleccionado>,
        precioUnitario: Double
    ) {
        this.precioUnitario = precioUnitario
        this._asientosSeleccionados = seats
        bloquearAsientos(eventoId, seats)
    }
    suspend fun bloquearAsientos(
        eventoId: Long,
        asientos: List<AsientoSeleccionado>
    ) {
        _uiState.value = SeatDetailsUiState.Blocking

        val result = ApiClient.bloquearAsientos(eventoId, asientos)

        result.fold(
            onSuccess = {
                _uiState.value = SeatDetailsUiState.Blocked
            },
            onFailure = { error ->
                _uiState.value = SeatDetailsUiState.Error(
                    error.message ?: "Error al bloquear asientos"
                )
            }
        )
    }

    @OptIn(ExperimentalTime::class)
    suspend fun venderAsientos(
        eventoId: Long,
        precioTotal: Double,
        persona: String
    ) {
        _uiState.value = SeatDetailsUiState.Selling

        val request = VentaAsientosRequest(
            eventoId = eventoId,
            fecha = Clock.System.now().toString(), // multiplataforma
            precioVenta = precioTotal,
            asientos = _asientosSeleccionados.map {
                AsientosComprasDTO(
                    fila = it.fila,
                    columna = it.columna,
                    persona = persona,
                )
            }
        )

        val result = ApiClient.venderAsientos(eventoId, request)

        result.fold(
            onSuccess = {
                _uiState.value = SeatDetailsUiState.Success
            },
            onFailure = { error ->
                _uiState.value = SeatDetailsUiState.Error(
                    error.message ?: "Error al vender asientos"
                )
            }
        )
    }

    fun total() =
        _asientosSeleccionados.size * precioUnitario
}