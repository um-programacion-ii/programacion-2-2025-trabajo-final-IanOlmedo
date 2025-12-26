package ar.edu.um.gestioneventos.shared.api.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventoLocalDto(
    val id: Long,
    val titulo: String,
    val fecha: String? = null,
    val resumen: String? = null,

    // puede venir camelCase
    val precioEntrada: Double? = null,

    // o snake_case
    @SerialName("precio_entrada")
    val precioEntradaSnake: Double? = null
) {
    fun resolvedPrecio(): Double = precioEntrada ?: precioEntradaSnake ?: 0.0
}

@Serializable
data class EventoRefDto(val id: Long)

@Serializable
data class AsientoLocalDto(
    val id: Long,
    val fila: String,
    val numero: Int,
    val estado: String? = null,

    // JHipster suele devolver la relación como objeto (puede venir null)
    @SerialName("evento_con_asientos")
    val eventoObj: EventoRefDto? = null,

    // o a veces como id directo (depende configuración)
    @SerialName("evento_con_asientos_id")
    val eventoIdSnake: Long? = null,

    @SerialName("eventoConAsientosId")
    val eventoIdCamel: Long? = null
) {
    fun resolvedEventoId(): Long? = eventoObj?.id ?: eventoIdCamel ?: eventoIdSnake
}

