package org.example.project.estados

sealed class SignUpState {
    object Estatico : SignUpState()
    object Cargando : SignUpState()
    data class Exitoso(val mensaje: String) : SignUpState()
    data class Error(val message: String) : SignUpState()
}
