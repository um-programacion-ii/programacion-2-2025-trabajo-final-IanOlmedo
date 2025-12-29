package com.mycompany.myapp.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import com.mycompany.myapp.service.dto.EventoTipoDTO;
@Data
public class EventoResumidoDTO implements Serializable {
    private String titulo;
    private String resumen;
    private String descripcion;
    private LocalDate fecha;
    private Double precioEntrada;
    private EventoTipoDTO eventoTipo;
    private Long id;
}
