package org.example.project.proxy
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.config.ApiConfig
import org.example.project.dto.EventoDTO

class EventoViewModel {

    private val _events = MutableStateFlow<List<EventoDTO>>(emptyList())
    val events: StateFlow<List<EventoDTO>> = _events.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun loadEvents() {
        _loading.value = true
        _error.value = null

        println("🎫 Token guardado: ${ApiClient.getToken()?.take(50)}...")
        println("🎫 Session guardado: ${ApiClient.getSessionToken()}")
        println("🚀 Cargando eventos desde: ${ApiConfig.baseUrl}/v1/service/eventos")

        val result = ApiClient.getEvents()

        result.fold(
            onSuccess = { eventList ->
                println("✅ Eventos cargados exitosamente: ${eventList.size}")
                _events.value = eventList
            },
            onFailure = { error ->
                println("❌ Error cargando eventos: ${error.message}")
                error.printStackTrace()

                val message = when (error) {
                    is HttpRequestTimeoutException -> "Tiempo de espera agotado"
                    is ConnectTimeoutException -> "No se pudo conectar al servidor"
                    is SocketTimeoutException -> "Conexión perdida"
                    else -> error.message ?: "Error al cargar eventos"
                }
                _error.value = message
                _events.value = emptyList()
            }
        )

        _loading.value = false
    }
}