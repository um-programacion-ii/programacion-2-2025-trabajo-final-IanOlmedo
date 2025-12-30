package org.example.project.proxy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.dto.SingUpDTO
import org.example.project.estados.SignUpState

class SingUpViewModel {

    private val _uiState = MutableStateFlow<SignUpState>(SignUpState.Estatico)
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    suspend fun registrar(login: String, password: String, email: String, firstName: String, lastName: String) {
        if (login.isBlank() || password.isBlank()) {
            _uiState.value = SignUpState.Error("Usuario y contraseña son requeridos")
            return
        }

        _uiState.value = SignUpState.Cargando

        val result = ApiClient.registrar(
            SingUpDTO(
                login,
                password,
                email,
                firstName,
                lastName,
                "es"
            )
        )

        result.fold(
            onSuccess = {
                _uiState.value = SignUpState.Exitoso("Registro exitoso")
            },
            onFailure = { error ->
                _uiState.value = SignUpState.Error(
                    error.message ?: "Error en el registro"
                )
            }
        )
    }

    fun resetState() {
        _uiState.value = SignUpState.Estatico
    }
}