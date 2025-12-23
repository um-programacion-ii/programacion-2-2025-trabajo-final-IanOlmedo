package ar.edu.um.gestioneventos.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean = true
)

@Serializable
data class AuthResponse(
    @SerialName("id_token")
    val idToken: String? = null
)
