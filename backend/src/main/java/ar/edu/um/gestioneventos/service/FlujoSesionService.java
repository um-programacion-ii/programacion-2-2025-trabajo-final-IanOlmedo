package ar.edu.um.gestioneventos.service;

import ar.edu.um.gestioneventos.domain.SesionUsuario;
import ar.edu.um.gestioneventos.domain.User;
import ar.edu.um.gestioneventos.repository.SesionUsuarioRepository;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlujoSesionService {

    private final SesionUsuarioRepository sesionUsuarioRepository;

    public FlujoSesionService(SesionUsuarioRepository sesionUsuarioRepository) {
        this.sesionUsuarioRepository = sesionUsuarioRepository;
    }

    public SesionUsuario guardarEstadoSesion(Long userId, String estadoFlujo, String datosTemporales) {
        Optional<SesionUsuario> existente = sesionUsuarioRepository.findOneByUser_Id(userId);

        SesionUsuario sesion = existente.orElseGet(SesionUsuario::new);


        if (sesion.getUser() == null) {
            User user = new User();
            user.setId(userId);
            sesion.setUser(user);
        }

        sesion.setEstadoFlujo(estadoFlujo);
        sesion.setDatosTemporales(datosTemporales);
        sesion.setUltimaActualizacion(Instant.now());

        return sesionUsuarioRepository.save(sesion);
    }

    public Optional<SesionUsuario> recuperarEstadoSesion(Long userId) {
        return sesionUsuarioRepository.findOneByUser_Id(userId);
    }

    public void limpiarSesion(Long userId) {
        sesionUsuarioRepository.findOneByUser_Id(userId)
            .ifPresent(sesionUsuarioRepository::delete);
    }
}
