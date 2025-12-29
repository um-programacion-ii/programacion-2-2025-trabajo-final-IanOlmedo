package org.example.project.Navegacion
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.interfaz.LoginInterfaz

class PantallaLogin : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LoginInterfaz(
            onLoginSuccess = {
                navigator.replace(PantallaEvento())
            },
            onGoToRegister = {
                navigator.push(PantallaRegistro())
            }
        )
    }
}


