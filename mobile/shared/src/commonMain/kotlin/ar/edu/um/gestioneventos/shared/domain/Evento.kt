package ar.edu.um.gestioneventos.shared.domain


data class Evento(
    val id: Long,
    val titulo: String,
    val fecha: String?,
    val resumen: String?,
    val precioEntrada: Double
)
