package org.example.project.estados

sealed class LoginState {
    object Estatico : LoginState()
    object Cargando : LoginState()
    data class Exitoso(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}