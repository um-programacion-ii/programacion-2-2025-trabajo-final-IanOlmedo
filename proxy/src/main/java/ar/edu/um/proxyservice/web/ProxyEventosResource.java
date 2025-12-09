package ar.edu.um.proxyservice.web;

import ar.edu.um.proxyservice.service.KafkaEventosService;
import ar.edu.um.proxyservice.service.RedisAsientosService;
import ar.edu.um.proxyservice.web.dto.AsientoProxyDTO;
import ar.edu.um.proxyservice.web.dto.EventoProxyDTO;
import ar.edu.um.proxyservice.web.dto.ReservaRequestDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador del proxy para exponer eventos, asientos y reservas.
 *
 * NOTA:
 *   - Los eventos provienen de mensajes de Kafka (cacheados en memoria).
 *   - Los asientos se consultan en Redis de la cátedra mediante el servicio RedisAsientosService.
 *   - Las reservas se publican en Kafka en el tópico configurado.
 */
@RestController
@RequestMapping("/api/proxy")
public class ProxyEventosResource {

    private static final Logger log = LoggerFactory.getLogger(ProxyEventosResource.class);

    private final KafkaEventosService kafkaEventosService;
    private final RedisAsientosService redisAsientosService;

    public ProxyEventosResource(
        KafkaEventosService kafkaEventosService,
        RedisAsientosService redisAsientosService
    ) {
        this.kafkaEventosService = kafkaEventosService;
        this.redisAsientosService = redisAsientosService;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("proxy-ok");
    }

    // GET /api/proxy/eventos
    // Por ahora devolvemos los eventos que el proxy haya recibido por Kafka y cacheado.
    @GetMapping("/eventos")
    public ResponseEntity<List<EventoProxyDTO>> listarEventos() {
        List<EventoProxyDTO> eventos = kafkaEventosService.listarEventosDesdeCache();
        return ResponseEntity.ok(eventos);
    }

    // GET /api/proxy/eventos/{id}/asientos
    // Usa Redis de la cátedra para obtener el estado actual de los asientos del evento.
    @GetMapping("/eventos/{id}/asientos")
    public ResponseEntity<List<AsientoProxyDTO>> obtenerAsientos(@PathVariable("id") Long eventoId) {
        log.info("[PROXY] Solicitando asientos para eventoId={}", eventoId);
        List<AsientoProxyDTO> asientos = redisAsientosService.obtenerAsientosPorEvento(eventoId);
        return ResponseEntity.ok(asientos);
    }

    // POST /api/proxy/reservas
    // Publica en Kafka la reserva recibida desde el backend.
    @PostMapping("/reservas")
    public ResponseEntity<String> registrarReserva(@RequestBody ReservaRequestDTO reserva) {
        log.info(
            "[PROXY] Reserva recibida - eventoId={}, usuarioId={}, asientos={}",
            reserva.getEventoId(),
            reserva.getUsuarioId(),
            reserva.getAsientosIds()
        );

        kafkaEventosService.publicarReserva(reserva);

        return ResponseEntity.accepted().body("Reserva enviada a Kafka");
    }
}
