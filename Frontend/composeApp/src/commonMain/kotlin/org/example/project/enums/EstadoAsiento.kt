package org.example.project.enums

import kotlinx.serialization.Serializable

@Serializable
enum class EstadoAsiento {
    DISPONIBLE,
    BLOQUEADO,
    VENDIDO
}
