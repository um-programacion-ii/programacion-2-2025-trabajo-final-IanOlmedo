package ar.edu.um.gestioneventos.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.gestioneventos.domain.SesionUsuario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SesionUsuarioDTO implements Serializable {

    private Long id;

    private String estadoFlujo;

    private String datosTemporales;

    @NotNull
    private Instant ultimaActualizacion;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoFlujo() {
        return estadoFlujo;
    }

    public void setEstadoFlujo(String estadoFlujo) {
        this.estadoFlujo = estadoFlujo;
    }

    public String getDatosTemporales() {
        return datosTemporales;
    }

    public void setDatosTemporales(String datosTemporales) {
        this.datosTemporales = datosTemporales;
    }

    public Instant getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Instant ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SesionUsuarioDTO)) {
            return false;
        }

        SesionUsuarioDTO sesionUsuarioDTO = (SesionUsuarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sesionUsuarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SesionUsuarioDTO{" +
            "id=" + getId() +
            ", estadoFlujo='" + getEstadoFlujo() + "'" +
            ", datosTemporales='" + getDatosTemporales() + "'" +
            ", ultimaActualizacion='" + getUltimaActualizacion() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
