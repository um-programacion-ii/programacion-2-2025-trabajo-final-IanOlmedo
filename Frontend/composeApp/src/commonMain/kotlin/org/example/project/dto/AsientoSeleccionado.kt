package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class AsientoSeleccionado(
    val fila: Int,
    val columna: Int
)