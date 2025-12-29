package org.example.project.dto

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class VentaAsientosRequest(
    val eventoId: Long,
    val fecha: String,
    val precioVenta: Double,
    val asientos: List<AsientosComprasDTO>
)
