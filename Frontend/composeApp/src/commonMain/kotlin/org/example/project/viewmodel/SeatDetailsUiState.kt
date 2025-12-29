package org.example.project.viewmodel

sealed class SeatDetailsUiState {
    object Idle : SeatDetailsUiState()
    object Blocking : SeatDetailsUiState()
    object Blocked : SeatDetailsUiState()
    object Selling : SeatDetailsUiState()
    object Success : SeatDetailsUiState()
    data class Error(val message: String) : SeatDetailsUiState()
}