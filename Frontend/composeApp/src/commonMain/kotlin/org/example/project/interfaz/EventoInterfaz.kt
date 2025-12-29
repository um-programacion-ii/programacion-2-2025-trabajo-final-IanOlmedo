package org.example.project.interfaz
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.project.dto.EventoDTO
import org.example.project.proxy.ModeloCompra
import org.example.project.proxy.ModeloEvento

@Composable
fun EventoInterfaz(
    onEventClick: (EventoDTO) -> Unit
) {
    val eventoViewModel = remember { ModeloEvento() }
    val comprasViewModel = remember { ModeloCompra() }
    val scope = rememberCoroutineScope()
    var mostrarCompras by remember { mutableStateOf(false) }

    val events by eventoViewModel.events.collectAsState()
    val loading by eventoViewModel.loading.collectAsState()
    val error by eventoViewModel.error.collectAsState()

    val compras by comprasViewModel.compras.collectAsState()
    val loadingCompras by comprasViewModel.loading.collectAsState()
    val errorCompras by comprasViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        eventoViewModel.loadEvents()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF121212), Color(0xFF242424))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    mostrarCompras = !mostrarCompras
                    if (mostrarCompras) {
                        comprasViewModel.loadCompras()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (mostrarCompras) Color(0xFF444444) else Color(0xFF007AFF)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (mostrarCompras) "Ocultar mis compras" else "Ver mis compras")
            }

            AnimatedVisibility(visible = mostrarCompras) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    when {
                        loadingCompras -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }
                        errorCompras != null -> {
                            Text(
                                text = errorCompras ?: "",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        compras.isEmpty() -> {
                            Text(
                                text = "No realizaste compras aún",
                                color = Color.Gray,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(
                                    items = compras,
                                    key = { it.id ?: it.hashCode() }
                                ) { compra ->
                                    CompraItem(compra)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Próximos Eventos",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    error != null -> {
                        ErrorView(error) {
                            scope.launch {
                                eventoViewModel.loadEvents()
                            }
                        }
                    }
                    events.isEmpty() -> {
                        Text(
                            text = "No hay eventos disponibles",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = events,
                                key = { event -> event.id }
                            ) { event ->
                                EventoItem(
                                    event = event,
                                    onClick = { onEventClick(event) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorView(error: String?, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.height(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Error: $error",
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007AFF)
            )
        ) {
            Text("Reintentar")
        }
    }
}