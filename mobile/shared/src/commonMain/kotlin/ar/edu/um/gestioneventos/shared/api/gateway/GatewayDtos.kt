package ar.edu.um.gestioneventos.shared.api.gateway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventoGatewayDto(
    val id: Long,
    @SerialName("nombre") val titulo: String,
    val fecha: String? = null,
    val resumen: String? = null,
    val precioEntrada: Double? = null
)

@Serializable
data class AsientoGatewayDto(
    val id: Long,
    val fila: String? = null,
    val numero: Int? = null,
    val estado: String? = null
)
