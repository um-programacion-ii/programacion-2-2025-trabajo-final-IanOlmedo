package com.project.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoResumidoResponse {
    private String titulo;
    private String resumen;
    private String descripcion;
    private Instant fecha;
    private Double precioEntrada;
    private EventoTipo eventoTipo;
    private Long id;
}
