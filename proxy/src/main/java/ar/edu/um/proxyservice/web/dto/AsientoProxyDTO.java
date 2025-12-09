package ar.edu.um.proxyservice.web.dto;

public class AsientoProxyDTO {

    private Long id;
    private String fila;
    private Integer numero;
    private String estado; // LIBRE, VENDIDO, BLOQUEADO, etc.

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
}
