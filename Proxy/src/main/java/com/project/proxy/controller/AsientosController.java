package com.project.proxy.controller;

import com.project.proxy.dto.AsientosCompletoDTO;
import com.project.proxy.dto.AsientosRedisDTO;
import com.project.proxy.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/proxy/asientos")
@Slf4j
public class AsientosController {
    @Autowired
    private RedisService redisService;

    /**
     * Obtiene todos los asientos de un evento con su estado
     */
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<AsientosRedisDTO> getAsientosEvento(@PathVariable Long eventoId) {
        log.info("Consultando asientos para evento {}", eventoId);
        AsientosRedisDTO asientos = redisService.getAsientosEvento(eventoId);
        return ResponseEntity.ok(asientos);
    }

    /**
     * Obtiene lista de identificadores de asientos NO disponibles
     */
    @GetMapping("/evento/{eventoId}/no-disponibles")
    public ResponseEntity<List<AsientosCompletoDTO>> getAsientosNoDisponibles(@PathVariable Long eventoId) {
        log.info("Consultando asientos no disponibles para evento {}", eventoId);
        List<AsientosCompletoDTO> noDisponibles = redisService.getAsientosNoDisponibles(eventoId);
        return ResponseEntity.ok(noDisponibles);
    }

    /**
     * Obtiene solo asientos vendidos
     */
    @GetMapping("/evento/{eventoId}/vendidos")
    public ResponseEntity<List<AsientosRedisDTO.AsientoRedis>> getAsientosVendidos(@PathVariable Long eventoId) {
        List<AsientosRedisDTO.AsientoRedis> vendidos = redisService.getAsientosVendidos(eventoId);
        return ResponseEntity.ok(vendidos);
    }

    /**
     * Obtiene solo asientos bloqueados válidos
     */
    @GetMapping("/evento/{eventoId}/bloqueados")
    public ResponseEntity<List<AsientosRedisDTO.AsientoRedis>> getAsientosBloqueados(@PathVariable Long eventoId) {
        List<AsientosRedisDTO.AsientoRedis> bloqueados = redisService.getAsientosBloqueados(eventoId);
        return ResponseEntity.ok(bloqueados);
    }

    /**
     * Obtiene estadísticas de un evento
     */
    @GetMapping("/evento/{eventoId}/estadisticas")
    public ResponseEntity<Map<String, Object>> getEstadisticas(@PathVariable Long eventoId) {
        Map<String, Object> stats = redisService.getEstadisticasEvento(eventoId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Endpoint de debug para ver todas las keys en Redis
     */
    @GetMapping("/debug/keys")
    public ResponseEntity<Set<String>> getAllKeys() {
        Set<String> keys = redisService.getAllKeys();
        return ResponseEntity.ok(keys);
    }

    /**
     * Endpoint de debug para ver el valor de una key específica
     */
    @GetMapping("/debug/key/{key}")
    public ResponseEntity<String> getKeyValue(@PathVariable String key) {
        String value = redisService.getValue(key);
        return ResponseEntity.ok(value);
    }

    /**
     * Endpoint de debug mejorado: devuelve TODAS las keys con sus valores
     */
    @GetMapping("/debug/all")
    public ResponseEntity<Map<String, String>> getAllKeysWithValues() {
        log.info("Consultando todas las keys con valores");

        try {
            Map<String, String> result = redisService.getAllKeysWithValues();
            log.info("Se obtuvieron {} keys", result.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error en getAllKeysWithValues: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

}
