package org.example.project.proxy
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.dto.LoginDTO
import org.example.project.estados.LoginState

class LoginViewModel{
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Estatico)
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    suspend fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginState.Error("Usuario y contraseña son requeridos")
            return
        }

        _uiState.value = LoginState.Cargando

        val result = ApiClient.login(LoginDTO(username, password))

        result.fold(
            onSuccess = { token ->
                _uiState.value = LoginState.Exitoso("Login exitoso")
            },
            onFailure = { error ->
                val message = when (error) {
                    is HttpRequestTimeoutException -> "Tiempo de espera agotado"
                    is ConnectTimeoutException -> "No se pudo conectar al servidor"
                    is SocketTimeoutException -> "Conexión perdida"
                    else -> error.message ?: "Error en el login"
                }
                _uiState.value = LoginState.Error(message)
            }
        )
    }

    fun resetState() {
        _uiState.value = LoginState.Estatico
    }

}
