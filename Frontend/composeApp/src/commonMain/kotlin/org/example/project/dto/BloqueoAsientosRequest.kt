package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class BloqueoAsientosRequest (
    val eventoId: Long,
    val asientos: List<AsientoSeleccionado>
)
