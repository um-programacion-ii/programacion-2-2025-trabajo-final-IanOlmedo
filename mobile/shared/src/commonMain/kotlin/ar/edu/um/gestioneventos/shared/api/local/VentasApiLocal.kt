package ar.edu.um.gestioneventos.shared.api.local


import ar.edu.um.gestioneventos.shared.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.headers

class VentasApiLocal(
    private val client: HttpClient,
    private val baseUrl: String = AppConfig.BASE_URL,
    private val tokenProvider: () -> String? = { null }
) {
    private fun authValue(): String? = tokenProvider()?.let { "Bearer $it" }

    suspend fun createVenta(request: VentaCreateRequest): VentaDto =
        client.post("$baseUrl/api/ventas") {
            authValue()?.let { token ->
                headers { append(HttpHeaders.Authorization, token) }
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}