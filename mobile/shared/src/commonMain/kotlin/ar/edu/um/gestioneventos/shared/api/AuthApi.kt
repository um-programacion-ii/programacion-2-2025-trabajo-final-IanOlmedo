package ar.edu.um.gestioneventos.shared.api

import ar.edu.um.gestioneventos.shared.config.AppConfig
import ar.edu.um.gestioneventos.shared.model.AuthRequest
import ar.edu.um.gestioneventos.shared.model.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(
    private val client: HttpClient,
    private val baseUrl: String = AppConfig.BASE_URL,
    private val tokenProvider: () -> String? = { null }
) {
    suspend fun login(request: AuthRequest): AuthResponse {
        return client.post("$baseUrl/api/authenticate") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
