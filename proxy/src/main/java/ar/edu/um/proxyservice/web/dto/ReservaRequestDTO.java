package ar.edu.um.proxyservice.web.dto;

import java.util.List;

public class ReservaRequestDTO {

    private Long eventoId;
    private Long usuarioId;
    private List<Long> asientosIds;

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
}
