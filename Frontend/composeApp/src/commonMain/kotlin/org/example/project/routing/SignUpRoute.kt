package org.example.project.routing

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.ui.SignUpScreen

class SignUpRoute : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SignUpScreen(
            onRegistroSuccess = {
                navigator.pop() // vuelve al login
            }
        )
    }
}
