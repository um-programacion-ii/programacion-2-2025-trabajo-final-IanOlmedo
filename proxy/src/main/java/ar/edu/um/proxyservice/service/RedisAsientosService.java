package ar.edu.um.proxyservice.service;

import ar.edu.um.proxyservice.web.dto.AsientoProxyDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Servicio para consultar el estado de los asientos desde Redis de la cátedra.
 *
 * Suposición:
 *   - Key por evento: "evento:{id}:asientos"
 *   - Value: JSON array con objetos que incluyen fila, columna, estado, etc.
 *
 * Ajustá el mapeo si el esquema real es diferente.
 */
@Service
public class RedisAsientosService {

    private static final Logger log = LoggerFactory.getLogger(RedisAsientosService.class);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisAsientosService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public List<AsientoProxyDTO> obtenerAsientosPorEvento(Long eventoId) {
        String key = "evento:" + eventoId + ":asientos"; // ajustar si el prefijo real es otro
        String json = redisTemplate.opsForValue().get(key);

        if (json == null) {
            log.info("[REDIS] No se encontró información de asientos para key={}", key);
            return Collections.emptyList();
        }

        try {
            JsonNode root = objectMapper.readTree(json);
            if (!root.isArray()) {
                log.warn("[REDIS] El valor para key={} no es un array JSON", key);
                return Collections.emptyList();
            }

            List<AsientoProxyDTO> result = new ArrayList<>();
            for (JsonNode node : root) {
                AsientoProxyDTO dto = new AsientoProxyDTO();

                if (node.has("id")) {
                    dto.setId(node.get("id").asLong());
                }

                if (node.has("fila")) {
                    dto.setFila(node.get("fila").asText());
                }

                // En el payload de la cátedra se habla de "fila" y "columna" :contentReference[oaicite:2]{index=2}
                if (node.has("columna")) {
                    dto.setNumero(node.get("columna").asInt());
                }

                if (node.has("estado")) {
                    dto.setEstado(node.get("estado").asText());
                }

                result.add(dto);
            }

            return result;
        } catch (JsonProcessingException e) {
            log.error("[REDIS] Error parseando JSON de asientos para key={}", key, e);
            return Collections.emptyList();
        }
    }
}
