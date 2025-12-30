package org.example.project

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.routing.LoginRoute

@Composable
fun App() {
    Navigator(screen = LoginRoute())
}