package ar.edu.um.gestioneventos.shared.repository

import ar.edu.um.gestioneventos.shared.api.gateway.EventosApiGateway
import ar.edu.um.gestioneventos.shared.api.local.EventosApiLocal
import ar.edu.um.gestioneventos.shared.config.AppConfig
import ar.edu.um.gestioneventos.shared.config.DataSourceMode
import ar.edu.um.gestioneventos.shared.domain.Asiento
import ar.edu.um.gestioneventos.shared.domain.Evento
import ar.edu.um.gestioneventos.shared.domain.ReservaRequest
import ar.edu.um.gestioneventos.shared.domain.ReservaResult

class EventosRepository(
    private val localApi: EventosApiLocal,
    private val gatewayApi: EventosApiGateway
) {
    suspend fun getEventos(): List<Evento> =
        when (AppConfig.DATA_SOURCE_MODE) {
            DataSourceMode.LOCAL_DB -> {
                localApi.getEventos().map {
                    Evento(
                        id = it.id,
                        titulo = it.titulo,
                        fecha = it.fecha,
                        resumen = it.resumen,
                        precioEntrada = it.resolvedPrecio()
                    )
                }
            }
            DataSourceMode.GATEWAY_CATEDRA -> {
                gatewayApi.getEventos().map {
                    Evento(
                        id = it.id,
                        titulo = it.titulo,
                        fecha = it.fecha,
                        resumen = it.resumen,
                        precioEntrada = it.precioEntrada ?: 0.0
                    )
                }
            }
        }

    suspend fun getAsientos(eventoId: Long): List<Asiento> =
        when (AppConfig.DATA_SOURCE_MODE) {
            DataSourceMode.LOCAL_DB -> {
                localApi.getAsientos()
                    .filter { it.resolvedEventoId() == eventoId }
                    .map {
                        Asiento(
                            id = it.id,
                            fila = it.fila,
                            numero = it.numero,
                            estado = it.estado
                        )
                    }
            }
            DataSourceMode.GATEWAY_CATEDRA -> {
                gatewayApi.getAsientos(eventoId).map {
                    Asiento(
                        id = it.id,
                        fila = it.fila ?: "?",
                        numero = it.numero ?: -1,
                        estado = it.estado
                    )
                }
            }
        }

    suspend fun reservar(request: ReservaRequest): ReservaResult =
        when (AppConfig.DATA_SOURCE_MODE) {
            DataSourceMode.LOCAL_DB -> {
                // Por ahora: modo local no reserva por gateway.
                // Cuando definamos el endpoint local de venta/reserva, lo implementamos acá.
                ReservaResult(
                    ok = false,
                    mensaje = "Reserva local aún no implementada (falta endpoint local de venta/reserva)."
                )
            }
            DataSourceMode.GATEWAY_CATEDRA -> {
                gatewayApi.reservar(request)
            }
        }
}
