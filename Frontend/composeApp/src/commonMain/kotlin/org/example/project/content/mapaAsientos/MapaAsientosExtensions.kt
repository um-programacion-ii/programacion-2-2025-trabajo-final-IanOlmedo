package org.example.project.content.mapaAsientos

import org.example.project.dto.MapaAsientosDTO
import org.example.project.enums.EstadoAsiento

fun MapaAsientosDTO.disponibles(): Int {
    return asientos.sumOf { fila ->
        fila.asientos.count { it.estado == EstadoAsiento.DISPONIBLE }
    }
}