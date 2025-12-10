package ar.edu.um.gestioneventos.service.dto.proxy;

import java.io.Serializable;

/**
 * DTO que representa un asiento tal como lo expone el proxy.
 */
public class AsientoProxyDTO implements Serializable {

    private Long id;
    private String fila;
    private Integer numero;
    private String estado;

    public AsientoProxyDTO() {
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "AsientoProxyDTO{" +
                "id=" + id +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado='" + estado + '\'' +
                '}';
    }
}
