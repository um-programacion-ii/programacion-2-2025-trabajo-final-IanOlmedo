package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.dto.LoginRequest
import org.example.project.network.ApiClient

class LoginViewModel {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    suspend fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Usuario y contraseña son requeridos")
            return
        }

        _uiState.value = LoginUiState.Loading

        val result = ApiClient.login(LoginRequest(username, password))

        result.fold(
            onSuccess = { token ->
                _uiState.value = LoginUiState.Success("Login exitoso")
            },
            onFailure = { error ->
                val message = when (error) {
                    is HttpRequestTimeoutException -> "Tiempo de espera agotado"
                    is ConnectTimeoutException -> "No se pudo conectar al servidor"
                    is SocketTimeoutException -> "Conexión perdida"
                    else -> error.message ?: "Error en el login"
                }
                _uiState.value = LoginUiState.Error(message)
            }
        )
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}