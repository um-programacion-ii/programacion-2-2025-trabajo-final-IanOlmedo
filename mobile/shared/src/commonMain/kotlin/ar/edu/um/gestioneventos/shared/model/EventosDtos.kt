package ar.edu.um.gestioneventos.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Evento(
    val id: Long,
    @SerialName("nombre") val titulo: String,
    val descripcion: String? = null,
    val fecha: String? = null,
    val lugar: String? = null,
    val precioBase: Double? = null,
    val imagen: String? = null
)

@Serializable
data class Asiento(
    val id: Long,
    val fila: String? = null,
    val numero: Int? = null,
    val nombre: String? = null,
    val precio: Double,
    val ocupado: Boolean = false
)

@Serializable
data class ReservaRequest(
    val eventoId: Long,
    val asientoIds: List<Long>
)

@Serializable
data class Reserva(
    val id: Long,
    val eventoId: Long,
    val asientoIds: List<Long>,
    val total: Double,
    val codigo: String? = null
)
