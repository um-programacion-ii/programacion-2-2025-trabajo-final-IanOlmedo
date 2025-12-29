package org.example.project.dto

import kotlinx.serialization.Serializable
import org.example.project.enums.EstadoAsiento

@Serializable
data class AsientoDTO(
    val columna: Int,
    val estado: EstadoAsiento
)