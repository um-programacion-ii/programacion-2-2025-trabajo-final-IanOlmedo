package org.example.project.dto

import kotlinx.serialization.Serializable

@Serializable
data class AsientosVentasDTO (
    val fila: Int,
    val columna: Int,
    val persona: String,
    val estado: String ?= null
)
