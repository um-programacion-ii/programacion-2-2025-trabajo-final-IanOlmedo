package org.example.project.dto

import org.example.project.enums.EstadoAsiento
import kotlinx.serialization.Serializable

@Serializable
data class AsientoCompletoDTO (
    val fila: Int,
    val columna: Int,
    val estado: String,
)