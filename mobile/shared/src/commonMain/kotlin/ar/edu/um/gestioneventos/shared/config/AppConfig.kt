package ar.edu.um.gestioneventos.shared.config

object AppConfig {
    // Emulador Android: 10.0.2.2 apunta a tu localhost.
    // Ajustalo si corrés backend en otra máquina.
    const val BASE_URL: String = "http://10.0.2.2:8081"

    // Switch de fuente (por ahora dejalo en LOCAL_DB)
    val DATA_SOURCE_MODE: DataSourceMode = DataSourceMode.LOCAL_DB // Cambiar a GATEWAY_CATEDRAn cuando funcione
}