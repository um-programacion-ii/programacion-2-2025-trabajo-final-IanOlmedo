package org.example.project.ui
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.RegisterUiState
import org.example.project.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = RegisterViewModel()) {

    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = login, onValueChange = { login = it }, label = { Text("Usuario") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Button(
            onClick = { viewModel.register(login, email, password) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Registrarse")
        }

        when (state) {
            is RegisterUiState.Loading -> CircularProgressIndicator()
            is RegisterUiState.Success -> Text("Registro exitoso")
            is RegisterUiState.Error -> Text("Error: ${(state as RegisterUiState.Error).message}")
            else -> {}
        }
    }
}