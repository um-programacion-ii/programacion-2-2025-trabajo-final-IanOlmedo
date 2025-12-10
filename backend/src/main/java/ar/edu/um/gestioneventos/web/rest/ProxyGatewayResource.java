package ar.edu.um.gestioneventos.web.rest;

import ar.edu.um.gestioneventos.service.ProxyGatewayService;
import ar.edu.um.gestioneventos.service.dto.proxy.AsientoProxyDTO;
import ar.edu.um.gestioneventos.service.dto.proxy.EventoProxyDTO;
import ar.edu.um.gestioneventos.service.dto.proxy.ReservaRequestDTO;

import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Resource REST del backend que expone la integración con el proxy.
 *
 * Todos los clientes (Angular, mobile) hablan con estos endpoints.
 * El backend, a su vez, delega en ProxyGatewayService para hablar con el proxy.
 */
@RestController
@RequestMapping("/api/proxy-gw")
public class ProxyGatewayResource {

    private static final Logger log = LoggerFactory.getLogger(ProxyGatewayResource.class);

    private final ProxyGatewayService proxyGatewayService;

    public ProxyGatewayResource(ProxyGatewayService proxyGatewayService) {
        this.proxyGatewayService = proxyGatewayService;
    }

    /**
     * GET /api/proxy-gw/eventos : lista eventos obtenidos desde el proxy.
     */
    @GetMapping("/eventos")
    public ResponseEntity<List<EventoProxyDTO>> listarEventos() {
        log.debug("REST request to get eventos from proxy");
        List<EventoProxyDTO> eventos = proxyGatewayService.obtenerEventosDesdeProxy();
        return ResponseEntity.ok(eventos);
    }

    /**
     * GET /api/proxy-gw/eventos/{id}/asientos : lista asientos del evento obtenidos
     * desde el proxy.
     */
    @GetMapping("/eventos/{id}/asientos")
    public ResponseEntity<List<AsientoProxyDTO>> listarAsientos(@PathVariable("id") Long eventoId) {
        log.debug("REST request to get asientos from proxy for eventoId={}", eventoId);
        List<AsientoProxyDTO> asientos = proxyGatewayService.obtenerAsientosDesdeProxy(eventoId);
        return ResponseEntity.ok(asientos);
    }

    /**
     * POST /api/proxy-gw/reservas : envía una reserva al proxy.
     */
    @PostMapping("/reservas")
    public ResponseEntity<Void> registrarReserva(@RequestBody ReservaRequestDTO reserva) throws URISyntaxException {
        log.debug("REST request to send reserva to proxy : {}", reserva);

        proxyGatewayService.enviarReservaAlProxy(reserva);

        // Puedes devolver 202 ACCEPTED, o 201 con una Location ficticia, según el
        // diseño.
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
