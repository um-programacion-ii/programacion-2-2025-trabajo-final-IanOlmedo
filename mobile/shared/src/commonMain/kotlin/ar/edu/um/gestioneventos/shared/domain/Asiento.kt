package ar.edu.um.gestioneventos.shared.domain



data class Asiento(
    val id: Long,
    val fila: String,
    val numero: Int,
    val estado: String?
)
