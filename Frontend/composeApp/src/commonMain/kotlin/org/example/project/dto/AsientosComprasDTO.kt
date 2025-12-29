package org.example.project.dto

import kotlinx.serialization.Serializable

@Serializable
data class AsientosComprasDTO (
    val fila: Int,
    val columna: Int,
    val persona: String,
)