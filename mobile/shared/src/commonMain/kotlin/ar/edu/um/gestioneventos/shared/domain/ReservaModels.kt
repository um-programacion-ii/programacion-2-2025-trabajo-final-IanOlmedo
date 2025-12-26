package ar.edu.um.gestioneventos.shared.domain

data class ReservaRequest(
    val eventoId: Long,
    val asientoIds: List<Long>
)

data class ReservaResult(
    val ok: Boolean = true,
    val mensaje: String? = null,
    val codigo: String? = null,
    val total: Double? = null
)
