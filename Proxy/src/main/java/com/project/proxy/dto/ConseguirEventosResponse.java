package com.project.proxy.dto;

import java.time.LocalDate;

public class ConseguirEventosResponse {
    private String titulo;
    private String resumen;
    private String descripcion;
    private LocalDate fecha;
    private String direccion;
    private String imagen;
    private Integer filaAsientos;;
    private Integer columnaAsientos;
    private Double precioEntrada;
    private EventoTipo eventoTipo;
    private IntegrantesDTO integrantesDTO;
    private Long id;
}
