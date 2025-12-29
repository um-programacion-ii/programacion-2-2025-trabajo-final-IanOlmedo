package com.project.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloqueoAsientosRequest {
    private Long eventoId;
    private List<AsientosDTO> asientos;
}
