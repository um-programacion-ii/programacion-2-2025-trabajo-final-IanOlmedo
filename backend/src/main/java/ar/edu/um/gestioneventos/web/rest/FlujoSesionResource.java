package ar.edu.um.gestioneventos.web.rest;

import ar.edu.um.gestioneventos.domain.SesionUsuario;
import ar.edu.um.gestioneventos.service.FlujoSesionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sesion")
public class FlujoSesionResource {

    private final FlujoSesionService flujoSesionService;

    public FlujoSesionResource(FlujoSesionService flujoSesionService) {
        this.flujoSesionService = flujoSesionService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<SesionUsuario> guardarEstado(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        String estadoFlujo = (String) body.get("estadoFlujo");
        String datosTemporales = (String) body.get("datosTemporales");

        SesionUsuario sesion = flujoSesionService.guardarEstadoSesion(userId, estadoFlujo, datosTemporales);
        return ResponseEntity.ok(sesion);
    }

    @GetMapping("/recuperar/{userId}")
    public ResponseEntity<SesionUsuario> recuperar(@PathVariable Long userId) {
        return flujoSesionService.recuperarEstadoSesion(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/limpiar/{userId}")
    public ResponseEntity<Void> limpiar(@PathVariable Long userId) {
        flujoSesionService.limpiarSesion(userId);
        return ResponseEntity.noContent().build();
    }
}
