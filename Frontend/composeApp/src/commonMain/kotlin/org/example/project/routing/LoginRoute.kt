package org.example.project.routing
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.ui.LoginScreen

class LoginRoute : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LoginScreen(
            onLoginSuccess = {
                navigator.replace(EventListRoute())
            },
            onGoToRegister = {
                navigator.push(SignUpRoute())
            }
        )
    }
}


