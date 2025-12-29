package com.mycompany.myapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoCatedraDTO {
    private Long id;
    private String titulo;
    private String resumen;
    private String descripcion;
    private Instant fecha;
    private String direccion;
    private String imagen;
    private Integer filaAsientos;
    private Integer columnAsientos;
    private Double precioEntrada;
    private EventoTipoDTO eventoTipo;
    private List<IntegrantesCatedraDTO> integrantes;
}
