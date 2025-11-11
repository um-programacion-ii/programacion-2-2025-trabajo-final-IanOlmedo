package ar.edu.um.gestioneventos.service.dto;

import ar.edu.um.gestioneventos.domain.enumeration.EstadoAsiento;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.gestioneventos.domain.Asiento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AsientoDTO implements Serializable {

    private Long id;

    @NotNull
    private String fila;

    @NotNull
    private Integer numero;

    private EstadoAsiento estado;

    private EventoDTO evento_con_asientos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    public EventoDTO getEvento_con_asientos() {
        return evento_con_asientos;
    }

    public void setEvento_con_asientos(EventoDTO evento_con_asientos) {
        this.evento_con_asientos = evento_con_asientos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AsientoDTO)) {
            return false;
        }

        AsientoDTO asientoDTO = (AsientoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, asientoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AsientoDTO{" +
            "id=" + getId() +
            ", fila='" + getFila() + "'" +
            ", numero=" + getNumero() +
            ", estado='" + getEstado() + "'" +
            ", evento_con_asientos=" + getEvento_con_asientos() +
            "}";
    }
}
