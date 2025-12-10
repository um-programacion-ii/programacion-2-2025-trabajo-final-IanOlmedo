package ar.edu.um.gestioneventos.service.dto.proxy;

import java.io.Serializable;
import java.util.List;

/**
 * DTO para enviar una reserva al proxy.
 * Debe matchear el JSON que espera el endpoint /api/proxy/reservas.
 */
public class ReservaRequestDTO implements Serializable {

    private Long eventoId;
    private Long usuarioId;
    private List<Long> asientosIds;

    public ReservaRequestDTO() {
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Long> getAsientosIds() {
        return asientosIds;
    }

    public void setAsientosIds(List<Long> asientosIds) {
        this.asientosIds = asientosIds;
    }

    @Override
    public String toString() {
        return "ReservaRequestDTO{" +
                "eventoId=" + eventoId +
                ", usuarioId=" + usuarioId +
                ", asientosIds=" + asientosIds +
                '}';
    }
}
