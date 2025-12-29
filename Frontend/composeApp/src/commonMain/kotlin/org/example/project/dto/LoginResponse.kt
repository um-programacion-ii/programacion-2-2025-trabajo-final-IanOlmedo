package org.example.project.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val jwt: String,
    val sessionToken: String,
    val sessionId: Long
)