package com.mycompany.myapp.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mycompany.myapp.enumeration.Changes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoKafkaDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long eventoId;
    private Changes tipoCambio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;

    private Map<String, Object> datosModificados;
    private String descripcion;
}
