package com.mycompany.myapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoTipoDTO implements Serializable {
    private String Nombre;
    private String Descripcion;

}
