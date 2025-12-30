package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class AsientoStateDTO(
    val columna: Int,
    val estado: String
)