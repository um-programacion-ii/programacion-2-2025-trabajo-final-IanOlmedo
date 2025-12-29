package org.example.project.dto
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class VentaAsientosResponse (
    val eventoId: Long,
    val ventaId: Long ?= null,
    val fechaVenta: String,
    val asientos: List<AsientosVentasDTO>,
    val resultado: Boolean,
    val descripcion: String,
    val precioVenta: Double
)