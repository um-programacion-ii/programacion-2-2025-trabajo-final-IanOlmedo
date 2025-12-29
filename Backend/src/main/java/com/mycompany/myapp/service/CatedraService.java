package com.mycompany.myapp.service;

import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CatedraService {

    private final ProxyClient proxyClient;

    public CatedraService(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    public String registrar(String body) {
        return proxyClient.registrar(body);
    }

    public EventoCatedraDTO getEventoPorId(String id) {
        return proxyClient.getEventobyId(id);
    }

    public List<EventoCatedraDTO> getEventos() {
        return proxyClient.getEventos();
    }

    public List<EventoResumidoDTO> getEventosResumidos() {
        return proxyClient.getEventosResumidos();
    }

    public BloqueoAsientosResponse bloqueoAsiento(BloqueoAsientosRequest request){
        return proxyClient.bloqueoAsiento(request);
    }

    public AsientosRedisDTO getAsientos(Long eventoId) {
        return proxyClient.getAsientosEvento(eventoId);
    }

    public List<AsientosProxyCompletosDTO> getAsientosNoDisponibles(Long eventoId) {
        return proxyClient.getAsientosNoDisponibles(eventoId);
    }

    public List<AsientosRedisDTO> getAsientosVendidos(Long eventoId) {
        return proxyClient.getAsientosVendidos(eventoId);
    }

    public List<AsientosRedisDTO> getAsientosBloqueados(Long eventoId) {
        return proxyClient.getAsientosBloqueados(eventoId);
    }

    public Map<String, Object> getEstadisticasEvento(Long eventoId) {
        return proxyClient.getEstadisticasEvento(eventoId);
    }

    public Set<String> getAllKeys() {
        return proxyClient.getAllKeys();
    }

    public Map<String, String> getAllKeysWithValues() {
        return proxyClient.getAllKeysWithValues();
    }

    public VentaAsientosResponse realizarVenta(VentaAsientosRequest request) {
        return proxyClient.realizarVenta(request);
    }

    public List<VentaAsientosResponse> listarVentas() {
        return proxyClient.listarVentas();
    }

    public VentaAsientosResponse listarVentaPorId(Long id) {
        return proxyClient.listarVentaPorId(id);
    }


}
