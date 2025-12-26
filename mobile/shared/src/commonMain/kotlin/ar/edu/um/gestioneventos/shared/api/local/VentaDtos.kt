package ar.edu.um.gestioneventos.shared.api.local

import kotlinx.serialization.Serializable

@Serializable
data class IdRefDto(val id: Long)

/**
 * Request típico JHipster para entidades con relaciones:
 * - evento: { id }
 * - asientos: [{ id }, ...]
 *
 * fechaVenta: normalmente el backend la setea solo, la dejamos opcional
 * estado: si el backend lo setea, podés omitirlo; si no, lo mandamos.
 */
@Serializable
data class VentaCreateRequest(
    val fechaVenta: String? = null,
    val estado: String? = "PENDIENTE",
    val evento: IdRefDto,
    val asientos: List<IdRefDto> = emptyList()
)

@Serializable
data class VentaDto(
    val id: Long? = null,
    val fechaVenta: String? = null,
    val estado: String? = null,
    val evento: IdRefDto? = null,
    val asientos: List<IdRefDto> = emptyList(),
    val usuario: IdRefDto? = null
)
