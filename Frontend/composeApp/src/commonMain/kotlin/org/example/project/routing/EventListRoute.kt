package org.example.project.routing
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.ui.EventListScreen

class EventListRoute: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        EventListScreen(
            onEventClick = { event ->
                navigator.push(AsientosRoute(event))
            }
        )
    }
}