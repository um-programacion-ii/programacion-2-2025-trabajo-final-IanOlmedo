package com.project.proxy.domain;

import com.project.proxy.service.BackendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProxyKafkaListener {
    @Autowired
    private BackendNotificationService backendNotificationService;


    @KafkaListener(topics = "${kafka.topic.eventos}", groupId = "${kafka.consumer.group-id}")
    public void consumirEventoActualizado(String mensaje) {
        try {
            log.info("Notificación de Kafka recibida: {}", mensaje);

            backendNotificationService.notificarSincronizacionEventos(mensaje);

            log.info("Backend notificado exitosamente sobre cambios en eventos");

        } catch (Exception e) {
            log.error("Error procesando mensaje de Kafka: {}", e.getMessage(), e);
        }
    }
}
