package org.example.project.estados

sealed class VentaState {
    object Estatico : VentaState()
    object Bloqueando : VentaState()
    object Bloqueado : VentaState()
    object Vendiendo : VentaState()
    object Exitoso : VentaState()
    data class Error(val message: String) : VentaState()
}