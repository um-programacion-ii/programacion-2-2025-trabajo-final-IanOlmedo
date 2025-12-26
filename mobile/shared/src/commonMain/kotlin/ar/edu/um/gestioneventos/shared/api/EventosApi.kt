package ar.edu.um.gestioneventos.shared.api

import ar.edu.um.gestioneventos.shared.config.AppConfig
import ar.edu.um.gestioneventos.shared.model.Asiento
import ar.edu.um.gestioneventos.shared.model.Evento
import ar.edu.um.gestioneventos.shared.model.Reserva
import ar.edu.um.gestioneventos.shared.model.ReservaRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class EventosApi(
    private val client: HttpClient,
    private val baseUrl: String = AppConfig.BASE_URL,
    private val tokenProvider: () -> String? = { null }
) {
    private fun authHeader(): String? = tokenProvider()?.let { "Bearer $it" }

    suspend fun getEventos(): List<Evento> =
        client.get("$baseUrl/api/proxy-gw/eventos") {
            authHeader()?.let { header("Authorization", it) }
        }.body()

    suspend fun getAsientos(eventoId: Long): List<Asiento> =
        client.get("$baseUrl/api/proxy-gw/eventos/$eventoId/asientos") {
            authHeader()?.let { header("Authorization", it) }
        }.body()

    suspend fun createReserva(request: ReservaRequest): Reserva =
        client.post("$baseUrl/api/proxy-gw/reservas") {
            authHeader()?.let { header("Authorization", it) }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
