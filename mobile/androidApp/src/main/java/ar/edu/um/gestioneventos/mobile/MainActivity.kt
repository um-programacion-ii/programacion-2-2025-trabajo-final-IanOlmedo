package ar.edu.um.gestioneventos.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ar.edu.um.gestioneventos.mobile.shared.Greeting // Import from shared module
import ar.edu.um.gestioneventos.mobile.ui.theme.GestioneventosmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestioneventosmobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "KMP", // Updated name for clarity
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
