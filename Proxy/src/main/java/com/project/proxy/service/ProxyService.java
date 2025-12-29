package com.project.proxy.service;

import com.project.proxy.client.CatedraClient;
import com.project.proxy.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProxyService {

    private final CatedraClient catedraClient;

    public ProxyService(CatedraClient catedraClient) {
        this.catedraClient = catedraClient;
    }

    public RegistrarUsuarioResponse registarUsuario(RegistrarUsuarioRequest request) {
        return catedraClient.registarUsuario(request);
    }

    public LoginResponse loguearUsuario(LoginRequest request) {
        return catedraClient.loguearUsuario(request);
    }

    public List<EventoResumidoResponse> conseguirEventosResumidos() {
        return catedraClient.conseguirEventosResumidos();
    }

    public List<EventoResponse> conseguirEventos() {
        return catedraClient.conseguirEventos();
    }

    public EventoResponse conseguirEventosPorId(Long id) {
        return catedraClient.conseguirEventosPorId(id);
    }

    public BloqueoAsientosResponse bloquearAsientos(BloqueoAsientosRequest request) {
        return catedraClient.bloquearAsientos(request);
    }

    public VentaAsientosResponse realizarVenta(VentaAsientosRequest request) {
        return catedraClient.realizarVenta(request);
    }

    public List<VentaAsientosResponse> listarVentas() {
        return catedraClient.listarVentas();
    }

    public VentaAsientosResponse listarVentasPorId(Long Id) {
        return catedraClient.listarVentasPorUsuario(Id);
    }

}
