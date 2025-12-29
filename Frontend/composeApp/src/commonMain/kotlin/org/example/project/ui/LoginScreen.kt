package org.example.project.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.content.LoginScreenContent

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LoginScreenContent(
            onLoginSuccess = {
                // Navegar a la siguiente pantalla
                navigator.push(EventListScreen())
            }
        )
    }
}
