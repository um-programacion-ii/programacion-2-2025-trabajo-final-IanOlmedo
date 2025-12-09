package ar.edu.um.proxyservice.service;

import ar.edu.um.proxyservice.web.dto.EventoProxyDTO;
import ar.edu.um.proxyservice.web.dto.ReservaRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Servicio responsable de:
 *  - Consumir mensajes de Kafka de la cátedra (eventos / asientos).
 *  - Publicar mensajes de reservas cuando el backend llama al proxy.
 *
 *  NOTA: Usamos un cache en memoria para eventos, rellenado con los
 *  mensajes que llegan por Kafka. Si el formato real cambia, solo
 *  hay que ajustar el mapeo en parsearEvento().
 */
@Service
public class KafkaEventosService {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventosService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private final String topicEventos;
    private final String topicAsientos;
    private final String topicReservas;

    // Cache simple en memoria de eventos notificados por Kafka
    private final Map<Long, EventoProxyDTO> eventosCache = new ConcurrentHashMap<>();

    public KafkaEventosService(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper,
        @Value("${app.kafka.topic.eventos}") String topicEventos,
        @Value("${app.kafka.topic.asientos}") String topicAsientos,
        @Value("${app.kafka.topic.reservas}") String topicReservas
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topicEventos = topicEventos;
        this.topicAsientos = topicAsientos;
        this.topicReservas = topicReservas;
    }

    // ==========================
    // CONSUMERS
    // ==========================

    @KafkaListener(
        topics = "#{__listener.topicEventos}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onEventoMessage(String message) {
        log.info("[KAFKA] Mensaje de eventos recibido: {}", message);
        try {
            EventoProxyDTO dto = parsearEvento(message);
            if (dto.getId() != null) {
                eventosCache.put(dto.getId(), dto);
                log.info("[KAFKA] Evento cacheado con id={}", dto.getId());
            }
        } catch (Exception e) {
            log.warn("[KAFKA] No se pudo parsear el mensaje de evento", e);
        }
    }

    @KafkaListener(
        topics = "#{__listener.topicAsientos}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onAsientosMessage(String message) {
        // Por ahora solo lo registramos. Más adelante se podría
        // utilizar para invalidar caches o disparar notificaciones.
        log.info("[KAFKA] Mensaje de asientos recibido: {}", message);
    }

    // ==========================
    // PRODUCER
    // ==========================

    public void publicarReserva(ReservaRequestDTO reserva) {
        try {
            String payload = objectMapper.writeValueAsString(reserva);
            log.info("[KAFKA] Publicando reserva en topic {}: {}", topicReservas, payload);
            kafkaTemplate.send(topicReservas, payload);
        } catch (JsonProcessingException e) {
            log.error("[KAFKA] Error serializando reserva para enviar a Kafka", e);
            throw new IllegalStateException("No se pudo publicar la reserva en Kafka", e);
        }
    }

    // ==========================
    // API para el controlador
    // ==========================

    public List<EventoProxyDTO> listarEventosDesdeCache() {
        return new ArrayList<>(eventosCache.values());
    }

    public EventoProxyDTO buscarEventoEnCache(Long id) {
        return eventosCache.get(id);
    }

    // ==========================
    // MAPEOS
    // ==========================

    /**
     * Intenta mapear el JSON del mensaje de Kafka al EventoProxyDTO,
     * suponiendo un formato similar a los payloads de eventos de la cátedra
     * (titulo, resumen, fecha, precioEntrada, id, etc.). :contentReference[oaicite:1]{index=1}
     */
    private EventoProxyDTO parsearEvento(String message) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(message);

        EventoProxyDTO dto = new EventoProxyDTO();

        if (root.has("id")) {
            dto.setId(root.get("id").asLong());
        }

        if (root.has("titulo")) {
            dto.setTitulo(root.get("titulo").asText());
        }

        if (root.has("resumen")) {
            dto.setResumen(root.get("resumen").asText());
        }

        if (root.has("fecha") && !root.get("fecha").isNull()) {
            String fechaStr = root.get("fecha").asText();
            // El payload usa fecha con hora tipo 2025-11-10T11:00:00Z
            // Tomamos solo la parte de fecha (yyyy-MM-dd).
            try {
                String soloFecha = fechaStr.substring(0, 10);
                dto.setFecha(LocalDate.parse(soloFecha));
            } catch (DateTimeParseException | IndexOutOfBoundsException e) {
                log.warn("[KAFKA] No se pudo parsear la fecha del evento: {}", fechaStr, e);
            }
        }

        if (root.has("precioEntrada") && root.get("precioEntrada").isNumber()) {
            BigDecimal precio = root.get("precioEntrada").decimalValue();
            dto.setPrecioEntrada(precio);
        }

        return dto;
    }

    public String getTopicEventos() {
        return topicEventos;
    }

    public String getTopicAsientos() {
        return topicAsientos;
    }
}
