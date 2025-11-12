package ar.edu.um.gestioneventos.service.dto;

import ar.edu.um.gestioneventos.domain.enumeration.EstadoVenta;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ar.edu.um.gestioneventos.domain.Venta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VentaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaVenta;

    private EstadoVenta estado;

    private EventoDTO evento;

    private Set<AsientoDTO> asientos = new HashSet<>();

    private UserDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Instant fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public Set<AsientoDTO> getAsientos() {
        return asientos;
    }

    public void setAsientos(Set<AsientoDTO> asientos) {
        this.asientos = asientos;
    }

    public UserDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UserDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDTO)) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ventaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", estado='" + getEstado() + "'" +
            ", evento=" + getEvento() +
            ", asientos=" + getAsientos() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
