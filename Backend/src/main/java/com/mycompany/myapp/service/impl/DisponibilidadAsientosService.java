package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.CatedraService;
import com.mycompany.myapp.service.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DisponibilidadAsientosService {

    private final EventoRepository eventoRepository;
    private final CatedraService catedraService;

    public DisponibilidadAsientosService(
        EventoRepository eventoRepository,
        CatedraService catedraService) {

        this.eventoRepository = eventoRepository;
        this.catedraService = catedraService;
    }

    public MapaAsientosDTO obtenerMapaAsientos(Long eventoId) {

        Evento evento = eventoRepository.findById(eventoId)
            .orElseThrow(() -> new IllegalArgumentException("Evento inexistente"));

        // Redis
        AsientosRedisDTO redisDTO = catedraService.getAsientos(eventoId);

        Map<String, String> estadoPorPosicion = new HashMap<>();

        for (AsientosRedisDTO.AsientoRedis a : redisDTO.getAsientos()) {
            String key = a.getFila() + "-" + a.getColumna();

            if ("Vendido".equalsIgnoreCase(a.getEstado())) {
                estadoPorPosicion.put(key, "VENDIDO");
            }
            else if (a.esBloqueadoValido()) {
                estadoPorPosicion.put(key, "BLOQUEADO");
            }
        }

        List<FilaAsientosDTO> filas = new ArrayList<>();

        for (int f = 1; f <= evento.getFilaAsientos(); f++) {
            List<AsientoEstadoDTO> columnas = new ArrayList<>();

            for (int c = 1; c <= evento.getColumnaAsientos(); c++) {
                String key = f + "-" + c;
                String estado = estadoPorPosicion.getOrDefault(key, "DISPONIBLE");

                columnas.add(new AsientoEstadoDTO(c, estado));
            }

            filas.add(new FilaAsientosDTO(f, columnas));
        }

        return new MapaAsientosDTO(
            eventoId,
            evento.getFilaAsientos(),
            evento.getColumnaAsientos(),
            filas
        );
    }

}
