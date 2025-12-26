package ar.edu.um.gestioneventos.shared.api.gateway

import ar.edu.um.gestioneventos.shared.config.AppConfig
import ar.edu.um.gestioneventos.shared.domain.ReservaRequest
import ar.edu.um.gestioneventos.shared.domain.ReservaResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.headers

class EventosApiGateway(
    private val client: HttpClient,
    private val baseUrl: String = AppConfig.BASE_URL,
    private val tokenProvider: () -> String? = { null }
) {
    private fun authValue(): String? = tokenProvider()?.let { "Bearer $it" }

    suspend fun getEventos(): List<EventoGatewayDto> =
        client.get("$baseUrl/api/proxy-gw/eventos") {
            authValue()?.let { token ->
                headers { append(HttpHeaders.Authorization, token) }
            }
        }.body()

    suspend fun getAsientos(eventoId: Long): List<AsientoGatewayDto> =
        client.get("$baseUrl/api/proxy-gw/eventos/$eventoId/asientos") {
            authValue()?.let { token ->
                headers { append(HttpHeaders.Authorization, token) }
            }
        }.body()

    suspend fun reservar(request: ReservaRequest): ReservaResult =
        client.post("$baseUrl/api/proxy-gw/reservas") {
            authValue()?.let { token ->
                headers { append(HttpHeaders.Authorization, token) }
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
