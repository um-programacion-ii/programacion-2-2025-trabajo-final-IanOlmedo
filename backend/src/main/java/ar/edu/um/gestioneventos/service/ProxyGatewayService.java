package ar.edu.um.gestioneventos.service;

import ar.edu.um.gestioneventos.service.dto.proxy.AsientoProxyDTO;
import ar.edu.um.gestioneventos.service.dto.proxy.EventoProxyDTO;
import ar.edu.um.gestioneventos.service.dto.proxy.ReservaRequestDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Servicio que actúa como "cliente" del proxy.
 * El backend nunca habla directo con Kafka/Redis, solo con este proxy.
 */
@Service
public class ProxyGatewayService {

    private static final Logger log = LoggerFactory.getLogger(ProxyGatewayService.class);

    private final WebClient proxyWebClient;

    public ProxyGatewayService(WebClient proxyWebClient) {
        this.proxyWebClient = proxyWebClient;
    }

    public List<EventoProxyDTO> obtenerEventosDesdeProxy() {
        log.debug("Solicitud de eventos al proxy");

        return proxyWebClient
                .get()
                .uri("/api/proxy/eventos")
                .retrieve()
                .bodyToFlux(EventoProxyDTO.class)
                .collectList()
                .block(); // bloqueamos porque el backend es MVC (sin reactor en los controllers)
    }

    public List<AsientoProxyDTO> obtenerAsientosDesdeProxy(Long eventoId) {
        log.debug("Solicitud de asientos al proxy para eventoId={}", eventoId);

        return proxyWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/proxy/eventos/{id}/asientos")
                        .build(eventoId))
                .retrieve()
                .bodyToFlux(AsientoProxyDTO.class)
                .collectList()
                .block();
    }

    public void enviarReservaAlProxy(ReservaRequestDTO reserva) {
        log.info(
                "Enviando reserva al proxy - eventoId={}, usuarioId={}, asientos={}",
                reserva.getEventoId(),
                reserva.getUsuarioId(),
                reserva.getAsientosIds());

        Mono<Void> mono = proxyWebClient
                .post()
                .uri("/api/proxy/reservas")
                .bodyValue(reserva)
                .retrieve()
                .bodyToMono(Void.class);

        // Bloqueamos para garantizar que el llamado se complete antes de seguir
        mono.block();
    }
}
