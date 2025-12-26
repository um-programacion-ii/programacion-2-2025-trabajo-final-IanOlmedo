package ar.edu.um.gestioneventos.shared.api.local

import ar.edu.um.gestioneventos.shared.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.headers

class EventosApiLocal(
    private val client: HttpClient,
    private val baseUrl: String = AppConfig.BASE_URL,
    private val tokenProvider: () -> String? = { null }
) {
    private fun authValue(): String? = tokenProvider()?.let { "Bearer $it" }

    suspend fun getEventos(): List<EventoLocalDto> =
        client.get("$baseUrl/api/eventos") {
            authValue()?.let { token ->
                headers { append(HttpHeaders.Authorization, token) }
            }
        }.body()

    suspend fun getAsientos(): List<AsientoLocalDto> =
        client.get("$baseUrl/api/asientos") {
            authValue()?.let { token ->
                headers { append(HttpHeaders.Authorization, token) }
            }
        }.body()
}
