package com.mycompany.myapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SesionRedisDTO implements Serializable {
    private String token;
    private Long sesionId;
    private Long usuarioId;

}
