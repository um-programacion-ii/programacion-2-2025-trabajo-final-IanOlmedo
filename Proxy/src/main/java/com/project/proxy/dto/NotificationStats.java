package com.project.proxy.dto;

import lombok.Data;

@Data
public class NotificationStats {
    private long notificacionesPendientes;
    private long notificacionesEnviadas;
    private long notificacionesFallidas;
}
