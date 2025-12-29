package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class BloqueoAsientosResponse(
    val resultado: Boolean,
    val descripcion: String,
    val eventoId: Long,
    val asientos: List<AsientoCompletoDTO>

)
