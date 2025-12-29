package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CatedraService;
import com.mycompany.myapp.service.dto.*;
import com.mycompany.myapp.service.impl.DisponibilidadAsientosService;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/service")
public class CatedraController {
    private final CatedraService catedraService;
    private final DisponibilidadAsientosService disponibilidadAsientosService;

    public CatedraController(CatedraService catedraService, DisponibilidadAsientosService disponibilidadAsientosService) {
        this.catedraService = catedraService;
        this.disponibilidadAsientosService = disponibilidadAsientosService;
    }

    @PostMapping("/registrar")
    public String registrar(@RequestBody String body) {
        return catedraService.registrar(body);
    }

    @GetMapping("/eventos/{id}")
    public EventoCatedraDTO getEvento(@PathVariable Long id) {
        return catedraService.getEventoPorId(id.toString());
    }

    @GetMapping("/eventos")
    public List<EventoCatedraDTO> getEventos() {
        return catedraService.getEventos();
    }

    @GetMapping("/eventos-resumidos")
    public List<EventoResumidoDTO> getEventosResumidos() {
        return catedraService.getEventosResumidos();
    }

    @GetMapping("/asientos/evento/{eventoId}")
    public AsientosRedisDTO getAsientosEvento(@PathVariable Long eventoId) {
        return catedraService.getAsientos(eventoId);
    }

    @GetMapping("/asientos/evento/{eventoId}/no-disponibles")
    public List<AsientosProxyCompletosDTO> getAsientosNoDisponibles(@PathVariable Long eventoId) {
        return catedraService.getAsientosNoDisponibles(eventoId);
    }
    @GetMapping("/asientos/evento/{eventoId}/vendidos")
    public List<AsientosRedisDTO> getAsientosVendidos(@PathVariable Long eventoId) {
        return catedraService.getAsientosVendidos(eventoId);
    }
    @GetMapping("/asientos/evento/{eventoId}/bloqueados")
    public List<AsientosRedisDTO> getAsientosBloqueados(@PathVariable Long eventoId) {
        return catedraService.getAsientosBloqueados(eventoId);
    }

    @GetMapping("/estadisticas/evento/{eventoId}")
    public Map<String, Object> getEstadisticasEvento(@PathVariable Long eventoId) {
        return catedraService.getEstadisticasEvento(eventoId);
    }

    @GetMapping("/debug/keys")
    public Set<String> getAllKeys() {
        return catedraService.getAllKeys();
    }

    @GetMapping("/debug/all")
    public Map<String, String> getAllKeysWithValues() {
        return catedraService.getAllKeysWithValues();
    }

    @PostMapping("/bloqueo-asientos")
    public BloqueoAsientosResponse bloqueoAsientos(@RequestBody BloqueoAsientosRequest body) {
        return catedraService.bloqueoAsiento(body);
    }

    @PostMapping("/realizar-venta")
    public VentaAsientosResponse realizarVenta(@RequestBody VentaAsientosRequest body) {
        return catedraService.realizarVenta(body);
    }

    @GetMapping("/listar-ventas")
    public List<VentaAsientosResponse> listarVentas() {
        return catedraService.listarVentas();
    }

    @GetMapping("/listar-ventas/{id}")
    public VentaAsientosResponse listarVentaPorId(@PathVariable Long id) {
        return catedraService.listarVentaPorId(id);
    }

    @GetMapping("/asientos/evento/{eventoId}/disponibles")
    public MapaAsientosDTO getAsientosDisponibles(
        @PathVariable Long eventoId
    ) {
        return disponibilidadAsientosService.obtenerMapaAsientos(eventoId);
    }
}
