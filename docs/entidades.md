# Modelo de Dominio – Backend JHipster

## Entidades creadas

- **Evento**
  - titulo, fecha, resumen, precioEntrada
  - Relación: 1:N con Asiento, 1:N con Venta

- **Asiento**
  - fila, numero, estado
  - Relación: N:1 con Evento

- **Venta**
  - fechaVenta, montoTotal, estado
  - Relación: N:1 con Evento, N:M con Asiento, N:1 con Usuario

- **SesionUsuario**
  - estadoFlujo, datosTemporales, ultimaActualizacion
  - Relación: 1:1 con Usuario

## Comando utilizado
```bash
jhipster entity <NombreEntidad>
