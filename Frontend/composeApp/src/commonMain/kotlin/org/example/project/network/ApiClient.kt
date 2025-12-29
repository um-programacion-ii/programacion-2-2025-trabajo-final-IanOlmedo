package org.example.project.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.config.ApiConfig
import org.example.project.dto.AsientoSeleccionado
import org.example.project.dto.BloqueoAsientosRequest
import org.example.project.dto.BloqueoAsientosResponse
import org.example.project.dto.EventoResponse
import org.example.project.dto.EventoResumidoResponse
import org.example.project.dto.LoginRequest
import org.example.project.dto.LoginResponse
import org.example.project.dto.MapaAsientosDTO
import org.example.project.dto.RegisterRequest
import org.example.project.dto.VentaAsientosRequest
import org.example.project.dto.VentaAsientosResponse

object ApiClient {
    private var jwtToken: String? = null
    private var sessionToken: String? = null  // ✅ Agrega esto

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        expectSuccess = false

        defaultRequest {
            url(ApiConfig.baseUrl)
            contentType(ContentType.Application.Json)
            println("🏗️ Base URL configurada: ${ApiConfig.baseUrl}")  // ← Log esto
        }
    }

    fun setTokens(jwt: String, session: String) {
        jwtToken = jwt
        sessionToken = session
        println("💾 JWT guardado: ${jwt.take(30)}...")
        println("💾 Session Token guardado: $session")
    }

    fun clearTokens() {
        jwtToken = null
        sessionToken = null
    }

    fun getToken(): String? = jwtToken
    fun getSessionToken(): String? = sessionToken

    // ✅ Helper para agregar AMBOS tokens
    private fun HttpRequestBuilder.addAuth() {
        jwtToken?.let { token ->
            header("Authorization", "Bearer $token")
            println("🔑 Authorization header agregado")
        }
        sessionToken?.let { session ->
            header("X-SESSION-TOKEN", session)
            println("🎫 X-SESSION-TOKEN header agregado")
        }

        if (jwtToken == null || sessionToken == null) {
            println("⚠️ Faltan tokens! JWT: ${jwtToken != null}, Session: ${sessionToken != null}")
        }
    }

    suspend fun login(request: LoginRequest): Result<String> {
        return try {
            println("🚀 Intentando login a: ${ApiConfig.baseUrl}/api/authenticate")
            println("📦 Request body: ${Json.encodeToString(request)}")

            val response: HttpResponse = client.post {
                url("${ApiConfig.baseUrl}/api/authenticate")
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            println("📡 Status code: ${response.status.value}")

            if (response.status.value in 200..299) {
                val loginResponse: LoginResponse = response.body()

                // ✅ Guarda AMBOS tokens
                setTokens(loginResponse.jwt, loginResponse.sessionToken)

                println("✅ Login exitoso!")
                println("📝 JWT guardado: ${loginResponse.jwt.take(30)}...")
                println("📝 Session guardado: ${loginResponse.sessionToken}")

                Result.success(loginResponse.jwt)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error: ${response.status} - $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción login: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getEvents(): Result<List<EventoResponse>> {
        return try {
            println("🚀 GET /v1/service/eventos")
            println("🔍 Tokens disponibles - JWT: ${jwtToken?.take(30)}, Session: $sessionToken")

            val response: HttpResponse = client.get("/api/v1/service/eventos") {
                addAuth()  // ✅ Agrega AMBOS tokens
            }

            println("📡 Status code: ${response.status.value}")
            println("📋 Headers enviados: ${response.request.headers.entries()}")

            if (response.status.value in 200..299) {
                val events: List<EventoResponse> = response.body()
                println("✅ ${events.size} eventos cargados")
                Result.success(events)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error ${response.status.value}: $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción getEvents: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getAsientosEvento(eventoId: Long): Result<MapaAsientosDTO> {
        return try {
            println("🚀 GET /api/v1/service/asientos/evento/$eventoId/disponibles")
            println("🔍 Tokens disponibles - JWT: ${jwtToken?.take(30)}, Session: $sessionToken")

            val response: HttpResponse = client.get("/api/v1/service/asientos/evento/$eventoId/disponibles") {
                addAuth()  // ✅ Agrega AMBOS tokens
            }

            println("📡 Status code: ${response.status.value}")
            println("📋 Headers enviados: ${response.request.headers.entries()}")

            if (response.status.value in 200..299) {
                val asientos: MapaAsientosDTO = response.body()
                Result.success(asientos)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error ${response.status.value}: $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción getAsientosEvento: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun bloquearAsientos(eventoId: Long, seats: List<AsientoSeleccionado>): Result<BloqueoAsientosResponse> {
        return try {
            println("🚀 Intentando bloquear asientos en: ${ApiConfig.baseUrl}/api/v1/service/bloqueo-asientos")
            println("📦 Request body: ${Json.encodeToString(seats)}")
            val request = BloqueoAsientosRequest(
                eventoId = eventoId,
                asientos = seats.map {
                    AsientoSeleccionado(it.fila, it.columna)
                }
            )
            val response: HttpResponse = client.post("${ApiConfig.baseUrl}/api/v1/service/bloqueo-asientos") {
                addAuth()  // ✅ Agrega AMBOS tokens
                setBody(request)
            }

            println("📡 Status code: ${response.status.value}")

            if (response.status.value in 200..299) {
                val bloqueoResponse: BloqueoAsientosResponse = response.body()

                println("✅ Bloqueo exitoso!")
                println("📝 BloqueoResponse: $bloqueoResponse")
                Result.success(bloqueoResponse)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error: ${response.status} - $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción bloqueo: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun venderAsientos(eventoId: Long, request: VentaAsientosRequest): Result<VentaAsientosResponse> {
        return try {
            println("🚀 Intentando bloquear asientos en: ${ApiConfig.baseUrl}/api/v1/service/realizar-venta")
            println("📦 Request body: ${Json.encodeToString(request)}")

            val response: HttpResponse = client.post("${ApiConfig.baseUrl}/api/v1/service/realizar-venta") {
                addAuth()  // ✅ Agrega AMBOS tokens
                setBody(request)
            }

            println("📡 Status code: ${response.status.value}")

            if (response.status.value in 200..299) {
                val ventaResponse: VentaAsientosResponse = response.body()

                println("✅ Venta exitosa!")
                println("📝 VentaResponse: $ventaResponse")
                Result.success(ventaResponse)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error: ${response.status} - $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción venta: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getEventsResumido(): Result<List<EventoResumidoResponse>> {
        return try {
            println("🚀 GET /v1/service/eventos-resumidos")
            val response: HttpResponse = client.get("/v1/service/eventos-resumidos") {
                addAuth()  // ✅ Agrega AMBOS tokens
            }

            println("📡 Status code: ${response.status.value}")

            if (response.status.value in 200..299) {
                val events: List<EventoResumidoResponse> = response.body()
                println("✅ ${events.size} eventos resumidos cargados")
                Result.success(events)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error: $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getEventById(id: Long): Result<EventoResponse> {
        return try {
            println("🚀 GET /v1/service/eventos/$id")
            val response: HttpResponse = client.get("/v1/service/eventos/$id") {
                addAuth()  // ✅ Agrega AMBOS tokens
            }

            println("📡 Status code: ${response.status.value}")

            if (response.status.value in 200..299) {
                val event: EventoResponse = response.body()
                println("✅ Evento cargado: ${event.titulo}")
                Result.success(event)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error: $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun register(request: RegisterRequest): Result<HttpResponse> {
        return try {
            val response = client.post("/register") {
                setBody(request)
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}