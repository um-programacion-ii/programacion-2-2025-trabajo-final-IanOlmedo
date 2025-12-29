package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.dto.RegisterRequest
import org.example.project.network.ApiClient

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(login: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {
                ApiClient.register(
                    RegisterRequest(login, password, email )
                )
                _uiState.value = RegisterUiState.Success
            } catch (e: Exception) {
                _uiState.value = RegisterUiState.Error(e.message ?: "Unknown error")
            }

            }
        }

}